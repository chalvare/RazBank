package com.razbank.razbank.models.account;

import com.razbank.razbank.entities.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class AccountModel  implements Serializable {

    private int id;

    private int accountNumber;

    private int status;

    private String tin;

    private float balance;

    private Customer customer;

}
