package com.netbank.inb.service;

import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.TransactionDto;
import com.netbank.inb.dto.TransactionFilterRequest;
import com.netbank.inb.entity.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {

    PageableResponse<TransactionDto> getFilteredTransactions(TransactionFilterRequest request);

}
