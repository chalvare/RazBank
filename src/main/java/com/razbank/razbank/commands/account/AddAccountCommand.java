package com.razbank.razbank.commands.account;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AddAccountCommand implements Command {

    private AddCustomerAccountRequest addCustomerAccountRequest;

    public abstract void saveAccount();
    public abstract void setAccountRequest(AddCustomerAccountRequest addCustomerAccountRequest);
}
