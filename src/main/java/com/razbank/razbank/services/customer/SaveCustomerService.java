package com.razbank.razbank.services.customer;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.responses.customer.SaveCustomerResponse;

import javax.servlet.http.HttpSession;

public interface SaveCustomerService {

    SaveCustomerResponse save(CustomerDTO customerDTO, HttpSession session);
}
