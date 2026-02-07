package com.netbank.inb.dto;

import com.netbank.inb.entity.Account;
import com.netbank.inb.entity.TransactionType;
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
public class TransactionDto {
    private Long id;

    private Account fromAccount;

    private Account toAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    private TransactionType type;

    @Column(length = 100)
    private String description;

    @Column
    private String referenceNumber;
}
