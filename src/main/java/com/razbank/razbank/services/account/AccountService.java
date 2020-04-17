package com.razbank.razbank.services.account;

import com.razbank.razbank.dtos.account.AccountDTO;
import com.razbank.razbank.responses.account.SaveAccountResponse;

public interface AccountService {

    SaveAccountResponse save(AccountDTO accountDTO);

}
