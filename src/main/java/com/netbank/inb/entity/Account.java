package com.netbank.inb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime lastTransactionDate;

    // Common fields for all account types
    private Boolean active = true;
}

