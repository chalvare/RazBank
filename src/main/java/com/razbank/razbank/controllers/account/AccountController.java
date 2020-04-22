package com.razbank.razbank.controllers.account;

import com.razbank.razbank.dtos.account.AccountDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.generic.RazBankException;
import com.razbank.razbank.responses.account.GetAccountsResponse;
import com.razbank.razbank.responses.account.SaveAccountResponse;
import com.razbank.razbank.services.account.AccountService;
import com.razbank.razbank.services.customer.CustomerService;
import com.razbank.razbank.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Account Controller Implementation</h1>
 * Class which works as a controller accounts
 * <p>
 * <b>Note:</b> N/A
 *
 * @author Christian Álvarez
 * @version 1.0
 * @since 2020-04-22
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private CustomerService customerService;
    private AccountService accountService;

    /**
     * Constructor
     *
     * @param customerService object
     * @param accountService object
     */
    @Autowired
    public AccountController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    /**
     * Get all accounts which belongs to customer
     *
     * @param customerId object
     * @return List<Account> object
     */
    @GetMapping("/account/{customerId}")
    public ResponseEntity<List<Account>> getCustomerAccounts(@PathVariable @Valid int customerId) {
        GetAccountsResponse response = accountService.findAccountsByCustomerId(customerId);
        return new ResponseEntity<>(response.getAccountList(), HttpStatus.OK);
    }

    /**
     * Save new account in the customer with id
     *
     * @param customerId object
     * @param accountDTO object
     * @return AccountDTO object
     */
    @PatchMapping("/account/{customerId}")
    public ResponseEntity<AccountDTO> addAccountToCustomer(@PathVariable int customerId,
                                                           @RequestBody AccountDTO accountDTO) {
        if (!Validator.validate(accountDTO)) {
            logger.error("INVALID ACCOUNT: {}", accountDTO);
            throw new RazBankException("INVALID ACCOUNT: " + accountDTO.toString());
        }
        Optional<Customer> customer = customerService.findById(customerId);
        accountDTO.setCustomer(customer
                .orElseThrow(() -> new RazBankException("Customer id not found - " + customerId)));

        SaveAccountResponse response = accountService.save(accountDTO);
        BeanUtils.copyProperties(response.getAccount(), accountDTO);
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);

    }

}
