package com.netbank.inb.dto;

import com.netbank.inb.entity.Account;
import com.netbank.inb.entity.User;
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
public class FixedDepositDto {
    private Long id;

    private Double principalAmount;

    private Double interestRate;

    private LocalDateTime maturityDate;

//    private User customer;

    private Account linkedAccount;
}
