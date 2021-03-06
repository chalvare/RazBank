package com.razbank.razbank.dtos.contactInformation;

import com.razbank.razbank.entities.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformationDTO implements Serializable {

    private int id;

    @NonNull
    private int addressType;

    @NonNull
    private String address;

    @NonNull
    private String addressNumber;

    @NonNull
    private String city;

    @NonNull
    private String postalCode;

    @NonNull
    private String country;

    @NonNull
    private String phone;

    private Customer customer;
}
