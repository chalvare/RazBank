package com.razbank.razbank.commands.account;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.requests.account.GetAccountsRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GetAccountsCommand implements Command {

    private GetAccountsRequest getAccountsRequest;

    public abstract void getAccounts();
    public abstract void setAccountsRequest(GetAccountsRequest getAccountsRequest);
}
