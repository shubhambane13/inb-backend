package com.netbank.inb.service.impl;

import com.netbank.inb.dto.FundTransferRequestDto;
import com.netbank.inb.dto.TransactionDto;
import com.netbank.inb.entity.Account;
import com.netbank.inb.entity.Transaction;
import com.netbank.inb.entity.TransactionType;
import com.netbank.inb.repository.AccountRepository;
import com.netbank.inb.repository.TransactionRepository;
import com.netbank.inb.service.FundTransferService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FundTransferServiceImpl implements FundTransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public TransactionDto processTransfer(Long loggedInCustomerId, FundTransferRequestDto request) {

        // 1. Basic Validations
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (request.getFromAccountNumber().equals(request.getToAccountNumber())) {
            throw new IllegalArgumentException("Cannot transfer funds to the same account.");
        }

        // 2. Fetch Accounts
        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found."));

        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found."));

        // 3. Security Check: Does the logged-in user actually own the 'fromAccount'?
        if (!fromAccount.getCustomer().getId().equals(loggedInCustomerId)) {
            throw new IllegalArgumentException("You are not authorized to transfer funds from this account.");
        }

        // 4. Status Check: Ensure both accounts are active
        if (!fromAccount.getActive() || !toAccount.getActive()) {
            throw new IllegalArgumentException("One or both accounts are currently inactive or locked.");
        }

        // 5. Balance Validation (Basic check)
        // Note: For Current accounts with overdraft, you would adjust this logic here[cite: 51].
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance in the source account.");
        }

        // 6. Execute Transfer (Update Balances)
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        fromAccount.setLastTransactionDate(LocalDateTime.now());

        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        toAccount.setLastTransactionDate(LocalDateTime.now());

        // Save accounts (JPA will batch these updates)
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // 7. Generate Transaction Record
        Transaction transaction = Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .type(TransactionType.TRANSFER) // Assuming you have an Enum for this
                .description(request.getRemarks() != null ? request.getRemarks() : "Online Fund Transfer")
                .referenceNumber("TRX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();

        return mapper.map(transactionRepository.save(transaction), TransactionDto.class);
    }
}
