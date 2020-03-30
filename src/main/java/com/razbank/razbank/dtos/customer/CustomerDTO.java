package com.razbank.razbank.dtos.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactInformation.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

    private int id;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String createDate;

    @NotNull
    private int typeCustomer;

    @NotNull
    private List<Account> accounts;

    @NotNull
    private ContactInformation contactInformation;

}
