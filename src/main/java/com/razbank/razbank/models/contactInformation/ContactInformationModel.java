package com.razbank.razbank.models.contactInformation;


import com.razbank.razbank.entities.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ContactInformationModel {

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

    private Customer customer;

}
