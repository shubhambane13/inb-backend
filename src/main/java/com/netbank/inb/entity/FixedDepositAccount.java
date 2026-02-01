package com.netbank.inb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("FD")
@Data
public class FixedDepositAccount extends Account {
    @Column(nullable = false)
    private Integer tenureMonths;  // 12, 24, 36 months

    private BigDecimal interestRate;  // 4.5%, 5.0%, 5.5%

    @Column(nullable = false)
    private LocalDate maturityDate;
}
