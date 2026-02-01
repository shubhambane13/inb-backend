package com.netbank.inb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_slips")
@Data
public class BankSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String slipNumber;     // Unique slip identifier

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private BigDecimal chequeAmount;

    @Column(nullable = false)
    private String chequeNumber;

    @Column(nullable = false)
    private LocalDateTime submittedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChequeStatus status = ChequeStatus.NOT_RECEIVED;

    @Column
    private LocalDateTime statusUpdatedDate;

    @Column
    private BigDecimal fineAmount;  // For bounced cheques
}

