package com.razbank.razbank.commands.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.entities.restriction.Restriction;
import com.razbank.razbank.entities.restriction.RestrictionIdentity;
import com.razbank.razbank.repositories.customer.CustomerRepository;
import com.razbank.razbank.repositories.restriction.RestrictionRepository;
import com.razbank.razbank.requests.createCustomers.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;
import com.razbank.razbank.riskengines.RestrictionEnum;
import com.razbank.razbank.riskengines.RiskEngines;
import com.razbank.razbank.riskengines.RiskEnginesEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class SaveCustomerAdultCommandImpl implements SaveCustomerCommand {

    private final CustomerRepository customerRepository;
    private final RestrictionRepository restrictionRepository;
    private CreateCustomerRequest createCustomerRequest;
    private boolean success;

    @Autowired
    public SaveCustomerAdultCommandImpl(/*@Qualifier("createCustomerAdultRequestImpl") CreateCustomerRequest customerCreateInfoRequest,*/
            CustomerRepository customerRepository, RestrictionRepository restrictionRepository) {
        this.customerRepository = customerRepository;
        this.restrictionRepository = restrictionRepository;
    }

    @Override
    public void saveCustomer(){
        try {
            CreateCustomerAdultRequestImpl createCustomerAdultRequest = (CreateCustomerAdultRequestImpl) createCustomerRequest;
            Customer customer = createCustomerAdultRequest.getCustomer();

            customer = customerRepository.save(customer);
            for (Account acc : customer.getAccounts()) {
                if (acc.getAccountNumber() == 0) {
                    acc.setAccountNumber((1000 * customer.getAccounts().size()) + customer.getId());
                }
            }
            List<RiskEnginesEnum> engines = loadEngines();
            if (!RiskEngines.crsEnginesExecution(engines, customer)) {
                Restriction res = Restriction.builder()
                        .restrictionIdentity(new RestrictionIdentity(customer.getId(), RestrictionEnum.ZCRS.getName()))
                        .build();
                restrictionRepository.save(res);
            }
            createCustomerAdultRequest.getSession().setAttribute("SESSION", customer);
            customerRepository.save(customer);
            this.setSuccess(true);
        }catch (Exception e){
            this.setSuccess(false);
        }
    }

    @Override
    public void execute() {
        saveCustomer();
    }

    @Override
    public void setCustomerRequest(CreateCustomerRequest createCustomerRequest) {
        this.createCustomerRequest=createCustomerRequest;
    }


    private List<RiskEnginesEnum> loadEngines() {
        List<RiskEnginesEnum> engines =  new ArrayList<>();
        engines.add(RiskEnginesEnum.CRS_PHONE);
        engines.add(RiskEnginesEnum.CRS_ADDRESS);
        return engines;
    }


}
