package com.razbank.razbank.services.customer;

import com.razbank.razbank.entities.customer.Customer;
import com.razbank.razbank.repositories.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAllByOrderById();
    }

    @Override
    public Optional<Customer> findById(int id) {

        return customerRepository.findById(id);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> searchByName(String name) {
        return customerRepository.findByNameContainsOrLastNameContainsAllIgnoreCase(name,name);
    }
}
