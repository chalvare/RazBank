package com.razbank.razbank.command.account;

import com.razbank.razbank.command.Command;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;

public interface AddAccountCommand extends Command {
    void saveAccount();
    void setAccountRequest(AddCustomerAccountRequest addCustomerAccountRequest);

}
