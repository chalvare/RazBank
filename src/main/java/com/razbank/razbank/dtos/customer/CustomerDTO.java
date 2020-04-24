package com.razbank.razbank.dtos.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactinformation.ContactInformation;
import com.razbank.razbank.entities.restriction.Restriction;
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

    private String createDate;

    private int typeCustomer;

    @NotNull
    private String placeOfBirth;

    @NotNull
    private String birthDate;

    @NotNull
    private String country;

    private String countryCode;

    private List<Account> accounts;

    private ContactInformation contactInformation;

    private List<Restriction> restrictions;
}
