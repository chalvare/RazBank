package com.razbank.razbank.riskengines.crs;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.contactInformation.ContactInformation;
import com.razbank.razbank.entities.customer.Customer;
import lombok.*;

import java.util.List;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class CrsPersonInfo {

    private String name;
    private String lastName;
    private ContactInformation contactInformation;
    private String country;
    private String birthDate;
    private String placeOfBirth;
    private List<Account> accounts;

    public CrsPersonInfo build(Customer customer){
        return CrsPersonInfo.builder()
                .name(customer.getName())
                .lastName(customer.getLastName())
                .contactInformation(customer.getContactInformation())
                .country(customer.getCountry())
                .accounts(customer.getAccounts())
                .build();
    }


}
