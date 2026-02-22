package com.netbank.inb.service.impl;

import com.netbank.inb.dto.AccountDto;
import com.netbank.inb.repository.AccountRepository;
import com.netbank.inb.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<AccountDto> getAccountsByUserId(Long userId) {
        return accountRepository.findByCustomerId(userId).stream().map(account -> mapper.map(account, AccountDto.class)).toList();
    }

    @Override
    public List<AccountDto> getAccountsByUserIdAndType(Long userId, String accountType) {
        return accountRepository.findByCustomerIdAndAccountType(userId, accountType).stream().map(account -> mapper.map(account, AccountDto.class)).toList();
    }
}
