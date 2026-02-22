package com.netbank.inb.service;

import com.netbank.inb.dto.FundTransferRequestDto;
import com.netbank.inb.dto.TransactionDto;

public interface FundTransferService {
    TransactionDto processTransfer(Long loggedInCustomerId, FundTransferRequestDto request);
}
