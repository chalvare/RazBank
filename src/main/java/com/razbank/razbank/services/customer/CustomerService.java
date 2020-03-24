package com.razbank.razbank.services.customer;

import com.razbank.razbank.entities.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public List<Customer> findAll();
    public Optional<Customer> findById(int id);
    public void save(Customer customer);
    public void deleteById(int id);
    public List<Customer>searchByName(String name);

}
