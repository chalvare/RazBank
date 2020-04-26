package com.razbank.razbank.repositories.account;

import com.razbank.razbank.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    List<Account> findAccountsByCustomerId(int customerId);

}
