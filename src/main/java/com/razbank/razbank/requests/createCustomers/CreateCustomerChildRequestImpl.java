package com.razbank.razbank.requests.createCustomers;

import com.razbank.razbank.entities.customer.Customer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CreateCustomerChildRequestImpl implements CreateCustomerRequest {

    @Override
    public void setSession(HttpSession session) {

    }

    @Override
    public void buildCustomer(Customer customer) {

    }
}
