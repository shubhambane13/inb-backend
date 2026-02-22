package com.netbank.inb.repository;

import com.netbank.inb.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCustomerId(Long userId);
    List<Account> findByCustomerIdAndAccountType(Long userId, String accountType);

}
