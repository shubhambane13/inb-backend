package com.netbank.inb.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("SAVINGS")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SavingsAccount extends Account {
    private BigDecimal interestRate;           // Configurable, same for all savings
    private BigDecimal dailyWithdrawalLimit;   // Configurable
    private BigDecimal minimumBalance;         // Configurable
}
