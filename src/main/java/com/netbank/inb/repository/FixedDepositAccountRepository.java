package com.netbank.inb.repository;

import com.netbank.inb.entity.FixedDepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedDepositAccountRepository extends JpaRepository<FixedDepositAccount, Long> {
}
