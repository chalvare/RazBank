package com.razbank.razbank.requests.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.entities.restriction.Restriction;
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
    private List<Restriction> restrictions;

    @Override
    public void buildCustomer(Customer customer) {

        this.customer = customer;
        this.accounts=customer.getAccounts();
        for(Account acc:accounts){
           acc.setCustomer(this.customer);
        }
        if(customer.getRestrictions()!=null) {
            this.restrictions = customer.getRestrictions();
            for (Restriction res : restrictions) {
                res.setCustomer(this.customer);
            }
        }
        this.customer.setAccounts(this.accounts);
        this.customer.setRestrictions(customer.getRestrictions());

    }


}
