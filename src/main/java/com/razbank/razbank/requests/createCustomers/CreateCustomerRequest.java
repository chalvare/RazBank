package com.razbank.razbank.requests.createCustomers;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.requests.Request;

public interface CreateCustomerRequest extends Request {
    void buildCustomer(CustomerDTO customerDTO);
}
