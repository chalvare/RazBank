package com.razbank.razbank.models.account;

import com.razbank.razbank.entities.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountModel {

    private int id;

    private int accountNumber;

    private int status;

    private Customer customer;
}
