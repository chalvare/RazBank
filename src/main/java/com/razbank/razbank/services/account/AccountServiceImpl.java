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

import java.util.ArrayList;
import java.util.List;


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
     * @return SaveAccountResponse
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
            buildSaveAccountResponse(response, account, e.getResponseInfo(), e.getMessage());
        }

        if (addAccountCommand.isSuccess()) {
            logger.error("SUCCESS");
            buildSaveAccountResponse(response, account, ResponseInfo.OK, ResponseInfo.OK.getValue());
        }

        return response;
    }


    /**
     * Method which gets accounts using customer id
     *
     * @param customerId int
     * @return GetAccountsResponse
     */
    @Override
    public GetAccountsResponse findAccountsByCustomerId(int customerId){
        logger.info("SERVICE: {}", CLASSNAME);
        GetAccountsResponse response = new GetAccountsResponse();
        Account account;

        try {
            GetAccountsRequest getAccountsRequest = new GetAccountsRequestImpl();
            account = Account.builder().customer(Customer.builder().id(customerId).build()).build();
            getAccountsRequest.buildAccount(account);
            getAccountsCommand.setAccountsRequest(getAccountsRequest);
            getAccountsCommand.execute();
        } catch (RazBankException e) {
            logger.error("RazBankException", e);
            buildGetAccountsResponse(response, customerId, new ArrayList<>(), e.getResponseInfo(), e.getMessage());
        }

        if (getAccountsCommand.isSuccess()) {
            logger.error("SUCCESS");
            buildGetAccountsResponse(response, customerId, getAccountsCommand.getAccountList(), ResponseInfo.OK, ResponseInfo.OK.getValue());
        }

        return response;

    }

    /**
     * Method which builds the command response
     *
     * @param response object
     * @param account object
     * @param responseInfo object
     * @param message object
     */
    private void buildSaveAccountResponse(SaveAccountResponse response, Account account, ResponseInfo responseInfo, String message) {
        response.setAccount(account);
        response.setResponseInfo(responseInfo);
        response.setMessage(message);
    }

    /**
     * Method which builds the command response
     *
     * @param response object
     * @param customerId object
     * @param accountList object
     * @param responseInfo object
     * @param message object
     */
    private void buildGetAccountsResponse(GetAccountsResponse response, int customerId, List<Account> accountList, ResponseInfo responseInfo, String message) {
        response.setCustomerId(customerId);
        response.setAccountList(accountList);
        response.setResponseInfo(responseInfo);
        response.setMessage(message);
    }

    /**
     * Method which builds an Account from DTO
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
