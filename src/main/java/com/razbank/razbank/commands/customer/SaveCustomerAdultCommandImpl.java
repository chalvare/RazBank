package com.razbank.razbank.commands.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.entities.restriction.Restriction;
import com.razbank.razbank.entities.restriction.RestrictionIdentity;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.repositories.customer.CustomerRepository;
import com.razbank.razbank.requests.customer.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.customer.CreateCustomerRequest;
import com.razbank.razbank.riskengines.RestrictionEnum;
import com.razbank.razbank.riskengines.RiskEngines;
import com.razbank.razbank.riskengines.RiskEnginesEnum;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Save customer adult command implementation</h1>
 * Command which executes the customer adult account addition
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-18
 */
@Component
@Getter
@Setter
public class SaveCustomerAdultCommandImpl extends SaveCustomerCommand {

    private static final String CLASSNAME = SaveCustomerAdultCommandImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(SaveCustomerAdultCommandImpl.class);

    private final CustomerRepository customerRepository;
    private CreateCustomerAdultRequestImpl createCustomerAdultRequestImpl;
    private Customer customer;
    private boolean success;

    /**
     * Constructor
     *
     * @param customerRepository    object
     */
    @Autowired
    public SaveCustomerAdultCommandImpl(/*@Qualifier("createCustomerAdultRequestImpl") CreateCustomerRequest customerCreateInfoRequest,*/
            CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Method which saves customer
     * @exception RazBankException exception
     */
    @Override
    public void saveCustomer()  {
        Customer cust = null;
        try {

            cust = createCustomerAdultRequestImpl.getCustomer();
            cust = customerRepository.save(cust);
            cust.getAccounts().add(Account.builder().customer(cust).build());
            for (Account acc : cust.getAccounts()) {
                if (acc.getAccountNumber() == 0) {
                    acc.setAccountNumber((1000 * cust.getAccounts().size()) + cust.getId());
                }
            }

            List<Restriction>res=addCustomerRestriction(cust);
            cust.setRestrictions(res);
            cust = customerRepository.save(cust);
            this.setCustomer(cust);
            this.setSuccess(true);

        } catch (Exception e) {
            this.setSuccess(false);
            String method = SaveCustomerAdultCommandImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.COMMAND_ERROR, CLASSNAME + ":" + method, cust);
        }
    }

    /**
     * Method which add customer restriction
     *
     * @param customer object
     * @return List<Restriction>
     * @exception RazBankException exception
     */
    private List<Restriction> addCustomerRestriction(Customer customer){
        List<Restriction> restrictionList = new ArrayList<>();
        try{
            List<RiskEnginesEnum> engines = loadEngines();
            if (!RiskEngines.crsEnginesExecution(engines, customer)) {
                Restriction res = Restriction.builder()
                        .restrictionIdentity(new RestrictionIdentity(customer.getId(), RestrictionEnum.ZCRS.getName()))
                        .build();
                restrictionList.add(res);
            }

        }catch (Exception e){
            this.setSuccess(false);
            String method = SaveCustomerAdultCommandImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.RISK_ENGINE_ERROR, CLASSNAME + ":" + method, customer);
        }

        return restrictionList;
    }

    /**
     * Method which load the risky engines
     *
     * @return List<RiskEnginesEnum>
     */
    private List<RiskEnginesEnum> loadEngines() {
        List<RiskEnginesEnum> engines = new ArrayList<>();
        engines.add(RiskEnginesEnum.CRS_PHONE);
        engines.add(RiskEnginesEnum.CRS_ADDRESS);
        return engines;
    }


    /**
     * Method which sets the CreateCustomerAdultRequestImpl request
     *
     * @param createCustomerRequest request
     */
    @Override
    public void setCustomerRequest(CreateCustomerRequest createCustomerRequest) {
        this.createCustomerAdultRequestImpl = (CreateCustomerAdultRequestImpl) createCustomerRequest;
    }

    /**
     * Method which executes the command
     *
     */
    @Override
    public void execute() {
        saveCustomer();
    }


}
