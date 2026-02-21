package com.netbank.inb.entity;

import com.netbank.inb.constant.AccountConstant;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue(AccountConstant.SAVING_ACCOUNT)
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SavingsAccount extends Account {
    private BigDecimal interestRate;
    private BigDecimal dailyWithdrawalLimit;
    private BigDecimal minimumBalance;
}
