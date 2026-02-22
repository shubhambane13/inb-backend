package com.netbank.inb.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionFilterRequest {
    private Long userId;            // Required: User ID
    private Long accountId;         // Optional: Specific account
    private LocalDateTime fromDate; // Optional: Start date
    private LocalDateTime toDate;   // Optional: End date

    // Pagination / Display flags
    private int page = 0;
    private int size = 10;
}
