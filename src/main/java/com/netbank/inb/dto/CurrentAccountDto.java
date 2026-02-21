package com.netbank.inb.dto;

import com.netbank.inb.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CurrentAccountDto {
    private Long id;

    private String accountNumber;

//    private User customer;

    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime createdDate;

    private LocalDateTime lastTransactionDate;

    private Boolean active = true;

    private Boolean zeroBalanceAllowed = false;

    private BigDecimal overdraftLimit;

    private BigDecimal overdraftInterestRate;
}
