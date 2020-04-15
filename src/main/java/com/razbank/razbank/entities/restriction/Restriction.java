package com.razbank.razbank.entities.restriction;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import lombok.experimental.Tolerate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="restriction")
@Data
@Builder
@With
public class Restriction implements Serializable {

    @EmbeddedId
    private RestrictionIdentity restrictionIdentity;


    @Tolerate
    Restriction() {}
}
