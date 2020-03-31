package com.razbank.razbank.command.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.repositories.customer.CustomerRepository;
import com.razbank.razbank.requests.createCustomers.CreateCustomerAdultRequestImpl;
import com.razbank.razbank.requests.createCustomers.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveCustomerCommandAdultCommand implements SaveCustomerCommand {

    private final CustomerRepository customerRepository;
    private CreateCustomerRequest createCustomerRequest;

    @Autowired
    public SaveCustomerCommandAdultCommand(/*@Qualifier("createCustomerAdultRequestImpl") CreateCustomerRequest customerCreateInfoRequest,*/
            CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void saveCustomer(){
        CreateCustomerAdultRequestImpl createCustomerAdultRequest = (CreateCustomerAdultRequestImpl) createCustomerRequest;
        Customer customer = createCustomerAdultRequest.getCustomer();

        customer=customerRepository.save(customer);
        for(Account acc: customer.getAccounts()){
            if(acc.getAccountNumber()==0) {
                acc.setAccountNumber((1000*customer.getAccounts().size())+customer.getId());
            }
        }
        createCustomerAdultRequest.getSession().setAttribute("SESSION", customer);
        customerRepository.save(customer);
    }

    @Override
    public void execute() {
        saveCustomer();
    }

    @Override
    public void setCustomerRequest(CreateCustomerRequest createCustomerRequest) {
        this.createCustomerRequest=createCustomerRequest;
    }


}
