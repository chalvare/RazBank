package com.razbank.razbank.commands.account;

import com.razbank.razbank.commands.Command;
import com.razbank.razbank.exceptions.RazBankException;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;

public interface AddAccountCommand extends Command {
    void saveAccount() throws RazBankException;
    void setAccountRequest(AddCustomerAccountRequest addCustomerAccountRequest);
}
