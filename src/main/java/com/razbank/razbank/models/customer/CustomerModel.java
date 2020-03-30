package com.razbank.razbank.models.customer;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactInformation.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel implements Serializable {

    private int id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Use only letters, please")
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Use only letters, please")
    private String lastName;

    @NotNull
    @Email(message = "Incorrect email format")
    private String email;

    @NotNull
    private String createDate;

    @NotNull
    @Range(min=0,max = 10)
    private int typeCustomer;

    private List<Account> accounts;

    @NotNull
    private ContactInformation contactInformation;


}
