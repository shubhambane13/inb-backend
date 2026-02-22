package com.netbank.inb.controller;

import com.netbank.inb.dto.FundTransferRequestDto;
import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.TransactionDto;
import com.netbank.inb.dto.TransactionFilterRequest;
import com.netbank.inb.service.FundTransferService;
import com.netbank.inb.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private FundTransferService fundTransferService;

    @PostMapping("/filter-transactions")
    public ResponseEntity<PageableResponse<TransactionDto>> getFilteredTransactions(@RequestBody TransactionFilterRequest
            request) {
        PageableResponse<TransactionDto> response = transactionService.getFilteredTransactions(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/fund-transfer")
    public TransactionDto transferFund(@RequestBody FundTransferRequestDto request) {
        return fundTransferService.processTransfer(request.getUserId(), request);
    }

}
