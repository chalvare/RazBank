package com.razbank.razbank.commands.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.entities.restriction.Restriction;
import com.razbank.razbank.entities.restriction.RestrictionIdentity;
import com.razbank.razbank.exceptions.RazBankException;
import com.razbank.razbank.repositories.customer.CustomerRepository;
import com.razbank.razbank.repositories.restriction.RestrictionRepository;
import com.razbank.razbank.requests.createCustomers.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;
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
    private final RestrictionRepository restrictionRepository;
    private CreateCustomerAdultRequestImpl createCustomerAdultRequestImpl;
    private Customer customer;
    private boolean success;

    /**
     * Constructor
     *
     * @param customerRepository object
     * @param restrictionRepository object
     */
    @Autowired
    public SaveCustomerAdultCommandImpl(/*@Qualifier("createCustomerAdultRequestImpl") CreateCustomerRequest customerCreateInfoRequest,*/
            CustomerRepository customerRepository, RestrictionRepository restrictionRepository) {
        this.customerRepository = customerRepository;
        this.restrictionRepository = restrictionRepository;
    }

    /**
     * Method which saves customer
     *
     * @throws RazBankException exception
     */
    @Override
    public void saveCustomer() throws RazBankException {
        Customer cust;
        try {

            cust = createCustomerAdultRequestImpl.getCustomer();
            cust = customerRepository.save(cust);
            for (Account acc : cust.getAccounts()) {
                if (acc.getAccountNumber() == 0) {
                    acc.setAccountNumber((1000 * cust.getAccounts().size()) + cust.getId());
                }
            }
            List<RiskEnginesEnum> engines = loadEngines();
            if (!RiskEngines.crsEnginesExecution(engines, cust)) {
                Restriction res = Restriction.builder()
                        .restrictionIdentity(new RestrictionIdentity(cust.getId(), RestrictionEnum.ZCRS.getName()))
                        .build();
                restrictionRepository.save(res);
            }
            createCustomerAdultRequestImpl.getSession().setAttribute("SESSION", cust);
            customerRepository.save(cust);
            this.setCustomer(cust);
            this.setSuccess(true);
        }catch (Exception e){
            this.setSuccess(false);
            String method = SaveCustomerAdultCommandImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.COMMAND_ERROR, CLASSNAME + ":" + method, this.customer);
        }
    }


    /**
     * Method which sets the CreateCustomerAdultRequestImpl request
     *
     * @param createCustomerRequest request
     */
    @Override
    public void setCustomerRequest(CreateCustomerRequest createCustomerRequest) {
        this.createCustomerAdultRequestImpl= (CreateCustomerAdultRequestImpl) createCustomerRequest;
    }

    /**
     * Method which executes the command
     *
     * @throws RazBankException exception
     */
    @Override
    public void execute() throws RazBankException {
        saveCustomer();
    }

    /**
     * Method which load the risky engines
     *
     * @return List<RiskEnginesEnum>
     */
    private List<RiskEnginesEnum> loadEngines() {
        List<RiskEnginesEnum> engines =  new ArrayList<>();
        engines.add(RiskEnginesEnum.CRS_PHONE);
        engines.add(RiskEnginesEnum.CRS_ADDRESS);
        return engines;
    }


}
