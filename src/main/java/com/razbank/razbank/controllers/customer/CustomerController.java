package com.razbank.razbank.controllers.customer;

import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.exceptions.customer.CustomerNotFoundException;
import com.razbank.razbank.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public List<Customer> listCustomers(){

        return customerService.findAll();

    }

    @GetMapping("/customer/{customerId}")
    public Customer getCustomer(@PathVariable int customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));
        System.out.println(customer.get().getAccounts());
        return customer.get();
    }


    @PostMapping("/customer")
    public Customer addCustomer(@RequestBody Customer customer){
        customer.setId(0);
        customerService.save(customer);

        return customer;
    }




    @PutMapping("/customer")
    public Customer updateCustomer(@Valid @RequestBody Customer customer){
        customerService.save(customer);

        return customer;
    }


    @DeleteMapping("/customer/{customerId}")
    public String deleteCustomer(@PathVariable int customerId){
        Optional<Customer> customer = customerService.findById(customerId);
        customer.orElseThrow( ()->new CustomerNotFoundException("Customer id not found - "+customerId));
        customerService.deleteById(customerId);
        return "Deleted customer id: " + customerId;
    }

}
