package com.razbank.razbank.entities.restriction;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class RestrictionIdentity implements Serializable {

    @NotNull
    @Column(name="customer_id")
    private int customerId;

    @NotNull
    @Column(name="restriction")
    private String restriction;


}
