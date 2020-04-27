package com.razbank.razbank.commands.account;

import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.repositories.account.AccountRepository;
import com.razbank.razbank.requests.account.AddCustomerAccountRequest;
import com.razbank.razbank.requests.account.AddCustomerAccountRequestImpl;
import com.razbank.razbank.utils.ResponseInfo;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <h1>Add account command implementation</h1>
 * Command which executes the customer account addition
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-17
 */
@Component
@Getter
@Setter
public class AddAccountCommandImpl extends AddAccountCommand {

    private static final String CLASSNAME = AddAccountCommandImpl.class.getSimpleName();
    private static final Logger logger = LoggerFactory.getLogger(AddAccountCommandImpl.class);

    private final AccountRepository accountRepository;
    private AddCustomerAccountRequestImpl addCustomerAccount;
    private Account account;
    private boolean success;

    /**
     * Constructor
     *
     * @param accountRepository repository
     */
    @Autowired
    public AddAccountCommandImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Method which saves account and stablishes account number
     *
     */
    @Override
    public void saveAccount() {
        Account acc = null;
        try {

            acc = addCustomerAccount.getAccount();
            acc.getCustomer().addAccount(acc);
            int accountNumber = 1000 * acc.getCustomer().getAccounts().size() + acc.getCustomer().getId();
            acc.setAccountNumber(accountNumber);
            accountRepository.save(acc);
            this.setAccount(acc);
            this.setSuccess(true);
        } catch (Exception e) {
            this.setSuccess(false);
            String method = AddAccountCommandImpl.class.getEnclosingMethod().getName();
            throw new RazBankException(e.getMessage(), ResponseInfo.COMMAND_ERROR, CLASSNAME + ":" + method, acc);
        }
    }

    /**
     * Method which sets the AddCustomerAccountRequest request
     *
     * @param addCustomerAccountRequest request
     */
    @Override
    public void setAccountRequest(AddCustomerAccountRequest addCustomerAccountRequest) {
        this.addCustomerAccount = (AddCustomerAccountRequestImpl) addCustomerAccountRequest;
    }

    /**
     * Method which executes the command
     *
     */
    @Override
    public void execute() {
        saveAccount();
    }
}
