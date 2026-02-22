package com.netbank.inb.dto;

import lombok.Data;

@Data
public class AccountRequest {
    private String accountType; // e.g., "CURRENT", "SAVINGS"
    private Long userId; // ID of the user to whom the account belongs
}
