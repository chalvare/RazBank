package com.razbank.razbank.requests.account;


import com.razbank.razbank.entities.account.Account;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GetAccountsRequestImpl implements GetAccountsRequest {

    Account account;

    @Override
    public void buildAccount(Account account) {
        this.account=account;
    }
}
