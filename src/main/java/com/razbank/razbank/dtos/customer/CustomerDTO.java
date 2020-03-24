package com.razbank.razbank.dtos.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactInformation.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

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
