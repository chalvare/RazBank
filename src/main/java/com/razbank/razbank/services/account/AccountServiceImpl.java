package com.razbank.razbank.services.account;

import com.razbank.razbank.commands.account.AddAccountCommandImpl;
import com.razbank.razbank.commands.account.GetAccountsCommandImpl;
import com.razbank.razbank.dtos.account.AccountDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.RazBankException;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;
import com.razbank.razbank.requests.account.AddCustomerAccountRequestImpl;
import com.razbank.razbank.requests.account.GetAccountsRequest;
import com.razbank.razbank.requests.account.GetAccountsRequestImpl;
import com.razbank.razbank.responses.account.GetAccountsResponse;
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
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-17
 */
@Service
public class AccountServiceImpl implements AccountService {


    private static final String CLASSNAME = AccountServiceImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AddAccountCommandImpl addAccountCommand;
    private final GetAccountsCommandImpl getAccountsCommand;


    /**
     * Contructor
     *
     * @param addAccountCommand implementation of AddAccountCommand
     * @param getAccountsCommand implementation of GetAccountsCommands
     */
    @Autowired
    public AccountServiceImpl(AddAccountCommandImpl addAccountCommand, GetAccountsCommandImpl getAccountsCommand) {
        this.addAccountCommand = addAccountCommand;
        this.getAccountsCommand = getAccountsCommand;
    }

    /**
     * This service method saves the account in database
     *
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
        } catch (RazBankException e) {
            logger.error("RazBankException", e);
            response.setAccount(account);
            response.setCode(e.getResponseInfo().getCode());
            response.setResponseInfo(e.getResponseInfo());
            response.setMessage(e.getMessage());
        }

        if (addAccountCommand.isSuccess()) {
            response.setAccount(account);
            response.setCode(ResponseInfo.OK.getCode());
            response.setResponseInfo(ResponseInfo.OK);
        }

        return response;
    }

    @Override
    public GetAccountsResponse findAccountsByCustomerId(int customerId){
        logger.info("SERVICE: {}", CLASSNAME);
        GetAccountsResponse response = new GetAccountsResponse();
        Account account = null;

        try {
            GetAccountsRequest getAccountsRequest = new GetAccountsRequestImpl();
            account = Account.builder().customer(Customer.builder().id(customerId).build()).build();
            getAccountsRequest.buildAccount(account);
            getAccountsCommand.setAccountsRequest(getAccountsRequest);
            getAccountsCommand.execute();
        } catch (RazBankException e) {
            logger.error("RazBankException", e);
            response.setCustomerId(customerId);
            response.setCode(e.getResponseInfo().getCode());
            response.setResponseInfo(e.getResponseInfo());
            response.setMessage(e.getMessage());
        }

        if (getAccountsCommand.isSuccess()) {
            response.setCustomerId(customerId);
            response.setAccountList(getAccountsCommand.getAccountList());
            response.setCode(ResponseInfo.OK.getCode());
            response.setResponseInfo(ResponseInfo.OK);
        }
        return response;

    }

    /**
     * Method which built an Account from DTO
     *
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
