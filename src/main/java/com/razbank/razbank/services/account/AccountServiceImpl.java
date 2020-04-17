package com.razbank.razbank.services.account;

import com.razbank.razbank.commands.account.AddAccountCommandImpl;
import com.razbank.razbank.dtos.account.AccountDTO;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;
import com.razbank.razbank.requests.account.AddCustomerAccountRequestImpl;
import com.razbank.razbank.responses.account.SaveAccountResponse;
import com.razbank.razbank.utils.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <h1>Account Service Implementation</h1>
 * Class which works as a service to save account in database
 * <p>
 * <b>Note:</b> N/A
 *
 * @author  Christian Álvarez
 * @version 1.0
 * @since   2020-04-17
 */
@Service
public class AccountServiceImpl implements AccountService {


    private static final String CLASSNAME = AccountServiceImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AddAccountCommandImpl addAccountCommand;

    /**
     * Contructor
     * @param addAccountCommand implementation of AddAccountCommand
     */
    @Autowired
    public AccountServiceImpl(AddAccountCommandImpl addAccountCommand) {
        this.addAccountCommand = addAccountCommand;
    }

    /**
     * This service method saves the account in database
     * @param accountDTO object
     * @return SaveAccountResponse: response of process
     */
    @Override
    public SaveAccountResponse save(AccountDTO accountDTO) {
        logger.info("SERVICE: {}", CLASSNAME);
        SaveAccountResponse response = new SaveAccountResponse();
        Account account = null;

        try {
            AddCustomerAccountRequest addCustomerAccountRequest = new AddCustomerAccountRequestImpl();
            account = buildAccount(accountDTO);
            addCustomerAccountRequest.buildAccount(account);
            addAccountCommand.setAccountRequest(addCustomerAccountRequest);
            addAccountCommand.execute();
        } catch (Exception e) {
            response.setAccount(account);
            response.setResponseInfo(ResponseInfo.ERROR);
            response.setMessage(e.getMessage());
        }

        if (addAccountCommand.isSuccess()) {
            response.setAccount(account);
            response.setResponseInfo(ResponseInfo.OK);
        }

        return response;
    }

    /**
     * Method which built an Account from DTO
     * @param accountDTO object
     * @return Account built with builder pattern
     */
    private Account buildAccount(AccountDTO accountDTO) {
        return Account.builder()
                .customer(accountDTO.getCustomer())
                .status(accountDTO.getStatus())
                .tin(accountDTO.getTin())
                .balance(accountDTO.getBalance())
                .accountNumber(accountDTO.getAccountNumber())
                .build();
    }
}
