package com.netbank.inb.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDto {
    private Long id;

    private String accountNumber;

    private String accountType;

    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime createdDate;

    private LocalDateTime lastTransactionDate;

    private Boolean active = true;

    private Boolean zeroBalanceAllowed = false;

    private BigDecimal overdraftLimit;

    private BigDecimal overdraftInterestRate;

    private BigDecimal interestRate;

    private BigDecimal dailyWithdrawalLimit;

    private BigDecimal minimumBalance;
}