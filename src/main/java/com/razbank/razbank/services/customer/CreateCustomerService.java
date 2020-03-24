package com.razbank.razbank.services.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.utils.Response;

public interface CreateCustomerService {

    public Response createCustomer(CustomerDTO customerDTO);
}
