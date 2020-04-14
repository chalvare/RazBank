package com.razbank.razbank.requests.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.requests.Request;

public interface AddCustomerAccountRequest extends Request {
    void buildAccount(Account account);
}
