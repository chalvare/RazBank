package com.razbank.razbank.responses.customer;

import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.responses.GenericResponse;
import lombok.Data;

@Data
public class SaveCustomerResponse extends GenericResponse {
    Customer customer;
}
