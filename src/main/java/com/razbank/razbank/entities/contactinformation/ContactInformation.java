package com.razbank.razbank.entities.contactinformation;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.razbank.razbank.entities.customer.Customer;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="contact_information")
@Data
@Builder
@With
public class ContactInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="address_type")
    private int addressType;

    @Column(name="address")
    private String address;

    @Column(name="address_number")
    private String addressNumber;

    @Column(name="city")
    private String city;

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="country")
    private String country;

    @Column(name="phone")
    private String phone;

    @OneToOne(mappedBy = "contactInformation", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Customer customer;

    @Tolerate
    ContactInformation() {}

}
