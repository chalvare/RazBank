package com.razbank.razbank.services.account;

import com.razbank.razbank.command.account.AddAccountCommand;
import com.razbank.razbank.dtos.account.AccountDTO;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;
import com.razbank.razbank.requests.account.AddCustomerAccountRequestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final static String CLASSNAME = AccountServiceImpl.class.getSimpleName();
    private final AddAccountCommand addAccountCommand;

    @Autowired
    public AccountServiceImpl(AddAccountCommand addAccountCommand) {
        this.addAccountCommand = addAccountCommand;
    }

    @Override
    public void save(AccountDTO accountDTO) {
        AddCustomerAccountRequest addCustomerAccountRequest = new AddCustomerAccountRequestImpl();
        Account account = Account.builder()
                .customer(accountDTO.getCustomer())
                .status(accountDTO.getStatus())
                .tin(accountDTO.getTin())
                .balance(accountDTO.getBalance())
                .accountNumber(accountDTO.getAccountNumber())
                .build();

        addCustomerAccountRequest.buildAccount(account);
        addAccountCommand.setAccountRequest(addCustomerAccountRequest);
        addAccountCommand.execute();
    }
}
