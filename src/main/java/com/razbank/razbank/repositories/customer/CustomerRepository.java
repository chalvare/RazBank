package com.razbank.razbank.repositories.customer;

import com.razbank.razbank.entities.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    List<Customer> findAllByOrderById();

    List<Customer> findByNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);

}
