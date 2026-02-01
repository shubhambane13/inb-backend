package com.netbank.inb.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CURRENT")
@Data
public class CurrentAccount extends Account {
    private Boolean zeroBalanceAllowed = false;
    private BigDecimal overdraftLimit;
    private BigDecimal overdraftInterestRate;  // Same for all current accounts
}
