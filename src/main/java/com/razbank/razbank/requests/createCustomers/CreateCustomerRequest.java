package com.razbank.razbank.requests.createCustomers;

import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.requests.Request;

import javax.servlet.http.HttpSession;

public interface CreateCustomerRequest extends Request {
    void setSession(HttpSession session);
    void buildCustomer(Customer customer);
}
