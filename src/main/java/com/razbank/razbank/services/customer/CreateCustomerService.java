package com.razbank.razbank.services.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.utils.Response;

import javax.servlet.http.HttpSession;

public interface CreateCustomerService {

    Response createCustomer(CustomerDTO customerDTO, HttpSession session);
}
