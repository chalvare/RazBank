package com.razbank.razbank.requests.createCustomers;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Data
public class CreateCustomerAdultRequestImpl implements CreateCustomerRequest {

    private HttpSession session;
    private Customer customer;
    private List<Account> accounts;

    @Override
    public void buildCustomer(Customer customer) {

        this.customer = customer;
        this.accounts=customer.getAccounts();
        for(Account acc:accounts){
           acc.setCustomer(this.customer);
        }
        this.customer.setAccounts(this.accounts);

    }



}
