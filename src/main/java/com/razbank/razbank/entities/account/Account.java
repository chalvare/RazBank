package com.razbank.razbank.entities.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.razbank.razbank.entities.customer.Customer;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name="account")
@Data
@Builder
@With
public class Account {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="account_number")
    private int accountNumber;

    @Column(name="status")
    private int status;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="customer_id")
    @JsonIgnore
    @ToString.Exclude
    private Customer customer;

    @Tolerate
    public Account() {
    }
}
