package com.razbank.razbank.commands.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.repositories.account.AccountRepository;
import com.razbank.razbank.requests.account.GetAccountsRequest;
import com.razbank.razbank.requests.account.GetAccountsRequestImpl;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <h1>Get customer's accounts command implementation</h1>
 * Command which executes the retrieving customer's account
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-18
 */
@Component
@Getter
@Setter
public class GetAccountsCommandImpl extends GetAccountsCommand {

    private static final String CLASSNAME = GetAccountsCommandImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(GetAccountsCommandImpl.class);

    private final AccountRepository accountRepository;
    private GetAccountsRequestImpl getAccountsRequestImpl;
    private List<Account> accountList;
    private boolean success;

    /**
     *
     * @param accountRepository repository
     */
    @Autowired
    public GetAccountsCommandImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /**
     * Method which retrieves a list customer's accounts
     *
     */
    @Override
    public void getAccounts() {
        List<Account> accounts=null;
        try {
            accounts = accountRepository.findAccountsByCustomerId(getAccountsRequestImpl.getAccount().getCustomer().getId());
            this.setAccountList(accounts);
            this.setSuccess(true);
        } catch (Exception e) {
            this.setSuccess(false);
            String method = GetAccountsCommandImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.COMMAND_ERROR, CLASSNAME + ":" + method, accounts);
        }
    }

    /**
     * Method which sets the GetAccountsRequest request
     *
     * @param getAccountsRequest request
     */
    @Override
    public void setAccountsRequest(GetAccountsRequest getAccountsRequest) {
        this.getAccountsRequestImpl = (GetAccountsRequestImpl) getAccountsRequest;
    }


    /**
     * Method which executes the command
     *
     */
    @Override
    public void execute() {
        getAccounts();
    }
}
