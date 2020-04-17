package com.razbank.razbank.responses.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.responses.GenericResponse;
import lombok.Data;

@Data
public class SaveAccountResponse extends GenericResponse {
    Account account;
}
