package com.razbank.razbank.models.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactInformation.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerModel {

    private int id;

    @NonNull
    private String name;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String createDate;

    @NonNull
    private int typeCustomer;

    private List<Account> accounts;

    @NonNull
    private ContactInformation contactInformation;


}
