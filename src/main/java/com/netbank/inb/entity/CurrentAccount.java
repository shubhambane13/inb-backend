package com.netbank.inb.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CURRENT")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CurrentAccount extends Account {
    private Boolean zeroBalanceAllowed = false;
    private BigDecimal overdraftLimit;
    private BigDecimal overdraftInterestRate;  // Same for all current accounts
}
