package com.razbank.razbank.dtos.account;

import com.razbank.razbank.entities.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {

    private int id;

    private int accountNumber;

    private int status;

    private String tin;

    private float balance;

    private Customer customer;

}
