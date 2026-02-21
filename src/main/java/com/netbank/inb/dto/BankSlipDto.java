package com.netbank.inb.dto;

import com.netbank.inb.entity.Account;
import com.netbank.inb.entity.ChequeStatus;
import com.netbank.inb.entity.User;
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
public class BankSlipDto {
    private Long id;

    private String slipNumber;

    private UserDto customer;

    private AccountDto account;

    private BigDecimal chequeAmount;

    private String chequeNumber;

    private LocalDateTime submittedDate;

    private ChequeStatus status = ChequeStatus.NOT_RECEIVED;

    private LocalDateTime statusUpdatedDate;

    private BigDecimal fineAmount;
}
