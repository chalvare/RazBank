package com.razbank.razbank.entities.restriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.razbank.razbank.entities.customer.Customer;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.With;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="restriction")
@Data
@Builder
@With
public class Restriction implements Serializable {

    @EmbeddedId
    private RestrictionIdentity restrictionIdentity;


    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="customer_id")
    @JsonIgnore
    @ToString.Exclude
    @MapsId("restrictionIdentity")
    private Customer customer;

    @Tolerate
    Restriction() {}
}
