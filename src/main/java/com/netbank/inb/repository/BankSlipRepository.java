package com.netbank.inb.repository;

import com.netbank.inb.entity.BankSlip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankSlipRepository extends JpaRepository<BankSlip, Long> {
}
