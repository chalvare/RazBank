package com.razbank.razbank.requests.createCustomers;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CreateCustomerAdultRequestImpl implements CreateCustomerRequest {

    private Customer customer;
    private Account account;

    @Override
    public void buildCustomer(CustomerDTO customerDTO) {

        Customer customer = Customer.builder()
                .name(customerDTO.getName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .createDate(customerDTO.getCreateDate())
                .typeCustomer(customerDTO.getTypeCustomer())
                .contactInformation(customerDTO.getContactInformation()).build();
        Account account = Account.builder().accountNumber(2).customer(customer).status(0).build();

        this.customer=customer;
        this.account=account;
        this.customer.add(this.account);
        this.account.setCustomer(this.customer);
    }

}
