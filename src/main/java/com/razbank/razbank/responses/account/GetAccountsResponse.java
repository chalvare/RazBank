package com.razbank.razbank.responses.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.responses.GenericResponse;
import lombok.Data;

import java.util.List;


@Data
public class GetAccountsResponse extends GenericResponse {
    private int customerId;
    private List<Account> accountList;
}
