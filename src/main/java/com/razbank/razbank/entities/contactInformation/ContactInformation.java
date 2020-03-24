package com.razbank.razbank.entities.contactInformation;


import com.razbank.razbank.entities.customer.Customer;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name="contact_information")
@Data
@Builder
@With
public class ContactInformation {

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

    @OneToOne(mappedBy = "contactInformation", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Customer customer;

    @Tolerate
    ContactInformation() {}

}
