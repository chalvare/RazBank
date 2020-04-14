package com.razbank.razbank.controllers.account;

import com.razbank.razbank.dtos.account.AccountDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CustomerNotFoundException;
import com.razbank.razbank.models.account.AccountModel;
import com.razbank.razbank.services.account.AccountService;
import com.razbank.razbank.services.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
    private CustomerService customerService;
    private AccountService accountService;

    @Autowired
    public AccountController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

   @GetMapping("/account/{customerId}")
    public ResponseEntity<List<Account>>getCustomerAccounts(@PathVariable int customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));
        return new ResponseEntity<>(customer.get().getAccounts(), HttpStatus.OK);

    }

    @PatchMapping("/account/{customerId}")
    public ResponseEntity<AccountModel> addAccountToCustomer(@PathVariable int customerId, @RequestBody AccountModel accountModel){
        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));

        AccountDTO accountDTO = new AccountDTO();
        accountModel.setCustomer(customer.get());
        BeanUtils.copyProperties(accountModel, accountDTO);
        accountService.save(accountDTO);
        return new ResponseEntity<>(accountModel, HttpStatus.CREATED);
    }




}
