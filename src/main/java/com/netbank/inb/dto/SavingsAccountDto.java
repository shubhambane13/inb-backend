package com.netbank.inb.dto;

import com.netbank.inb.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SavingsAccountDto {
    private Long id;

    private String accountNumber;

    private User customer;

    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime createdDate;

    private LocalDateTime lastTransactionDate;

    private Boolean active = true;

    private BigDecimal interestRate;           // Configurable, same for all savings

    private BigDecimal dailyWithdrawalLimit;   // Configurable

    private BigDecimal minimumBalance;         // Configurable
}
