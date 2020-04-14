package com.razbank.razbank.command.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.repositories.account.AccountRepository;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;
import com.razbank.razbank.requests.account.AddCustomerAccountRequestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddAccountCommandImpl implements AddAccountCommand{

    private final AccountRepository accountRepository;
    private AddCustomerAccountRequest addCustomerAccountRequest;

    @Autowired
    public AddAccountCommandImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void saveAccount() {
        AddCustomerAccountRequestImpl addCustomerAccount = (AddCustomerAccountRequestImpl) addCustomerAccountRequest;
        Account account = addCustomerAccount.getAccount();
        account.getCustomer().add(account);
        int accountNumber = 1000*account.getCustomer().getAccounts().size()+account.getCustomer().getId();
        account.setAccountNumber(accountNumber);
        accountRepository.save(account);
    }

    @Override
    public void setAccountRequest(AddCustomerAccountRequest addCustomerAccountRequest) {
        this.addCustomerAccountRequest = addCustomerAccountRequest;
    }

    @Override
    public void execute() {
        saveAccount();
    }
}
