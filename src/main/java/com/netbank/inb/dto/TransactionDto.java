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

    private AccountDto fromAccount;

    private AccountDto toAccount;

    private BigDecimal amount;

    private LocalDateTime transactionDate;

    private TransactionType type;

    private String description;

    private String referenceNumber;
}
