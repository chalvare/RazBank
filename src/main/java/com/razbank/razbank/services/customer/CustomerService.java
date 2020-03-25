package com.razbank.razbank.services.customer;

import com.razbank.razbank.entities.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();
    Optional<Customer> findById(int id);
    void save(Customer customer);
    void deleteById(int id);
    List<Customer>searchByName(String name);

}
