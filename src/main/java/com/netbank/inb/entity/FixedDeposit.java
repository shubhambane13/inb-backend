package com.netbank.inb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "fixed_deposit")
public class FixedDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double principalAmount;

    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private LocalDateTime maturityDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToOne
    @NotNull
    private Account linkedAccount;
}