package com.netbank.inb.repository;

import com.netbank.inb.entity.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {
}
