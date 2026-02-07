package com.netbank.inb.dto;

import com.netbank.inb.entity.User;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FixedDepositAccountDto {
    private Long id;

    private String accountNumber;

    private User customer;

    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime createdDate;

    private LocalDateTime lastTransactionDate;

    private Boolean active = true;

    private Integer tenureMonths;

    private BigDecimal interestRate;

    private LocalDate maturityDate;
}
