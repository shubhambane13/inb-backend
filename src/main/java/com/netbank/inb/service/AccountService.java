package com.netbank.inb.service;

import com.netbank.inb.dto.AccountDto;
import com.netbank.inb.dto.PageableResponse;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAccountsByUserId(Long userId);

    List<AccountDto> getAccountsByUserIdAndType(Long userId, String accountType);

}
