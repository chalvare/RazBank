package com.razbank.razbank.controllers.account;

import com.razbank.razbank.dtos.account.AccountDTO;
import com.razbank.razbank.entities.account.Account;
import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CustomerNotFoundException;
import com.razbank.razbank.responses.account.SaveAccountResponse;
import com.razbank.razbank.services.account.AccountService;
import com.razbank.razbank.services.customer.CustomerService;
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

    private CustomerService customerService;
    private AccountService accountService;

    @Autowired
    public AccountController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @GetMapping("/account/{customerId}")
    public ResponseEntity<List<Account>> getCustomerAccounts(@PathVariable int customerId) {
        Optional<Customer> customer = customerService.findById(customerId);
        return new ResponseEntity<>(
                customer
                        .orElseThrow(() -> new CustomerNotFoundException("Customer id not found - " + customerId))
                        .getAccounts()
                , HttpStatus.OK);
    }

    @PatchMapping("/account/{customerId}")
    public ResponseEntity<AccountDTO> addAccountToCustomer(@PathVariable int customerId,
                                                           @RequestBody AccountDTO accountDTO) {
        Optional<Customer> customer = customerService.findById(customerId);

        accountDTO.setCustomer(customer
                .orElseThrow(() -> new CustomerNotFoundException("Customer id not found - " + customerId)));
        SaveAccountResponse response = accountService.save(accountDTO);
        BeanUtils.copyProperties(response.getAccount(),accountDTO);
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

}
