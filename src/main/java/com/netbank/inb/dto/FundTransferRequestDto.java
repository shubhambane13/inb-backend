package com.netbank.inb.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FundTransferRequestDto {
    private Long userId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String remarks;
}