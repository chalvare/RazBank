package com.razbank.razbank.entities.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactInformation.ContactInformation;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
@Data
@Builder
@With
public class Customer implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="create_date")
    private String createDate;

    @Column(name="type_customer")
    private int typeCustomer;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @ToString.Exclude
    @JsonIgnore
    private List<Account> accounts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="contact_information_id")
    private ContactInformation contactInformation;

    @Tolerate
    Customer() {}

    public void add(Account account){
        if(accounts==null){
            accounts = new ArrayList<>();
        }
        accounts.add(account);
        account.setCustomer(this);
    }

}
