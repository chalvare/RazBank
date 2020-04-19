package com.razbank.razbank.requests.customer;

import com.razbank.razbank.entities.customer.Customer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CreateCustomerChildRequestImpl implements CreateCustomerRequest {

    @Override
    public void setSession(HttpSession session) {
        //to do
    }

    @Override
    public void buildCustomer(Customer customer) {
        //to do
    }
}
