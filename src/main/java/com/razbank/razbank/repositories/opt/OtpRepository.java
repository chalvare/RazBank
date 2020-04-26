package com.razbank.razbank.repositories.opt;

import com.razbank.razbank.entities.otp.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Integer> {
    Optional<Otp> findFirstByCustomerIdOrderByExpiryTimeDesc(int customerId);
    @Transactional long deleteByCustomerId(int integer);
}
