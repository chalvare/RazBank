package com.razbank.razbank.commands.account;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;

public interface AddAccountCommand extends Command {
    void saveAccount();
    void setAccountRequest(AddCustomerAccountRequest addCustomerAccountRequest);
}
