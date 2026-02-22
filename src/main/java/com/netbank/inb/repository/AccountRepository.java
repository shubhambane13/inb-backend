package com.netbank.inb.repository;

import com.netbank.inb.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerId(Long userId);
    List<Account> findByCustomerIdAndAccountType(Long userId, String accountType);

}
