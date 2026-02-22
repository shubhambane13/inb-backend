package com.netbank.inb.controller;

import com.netbank.inb.dto.AccountDto;
import com.netbank.inb.dto.AccountRequest;
import com.netbank.inb.dto.PagingRequest;
import com.netbank.inb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/get-by-user-id")
    public List<AccountDto> getAccountsByUserId(@RequestBody AccountRequest request) {
        return this.accountService.getAccountsByUserId(request.getUserId());
    }

    @PostMapping("/get-by-user-id-account-type")
    public List<AccountDto> getAccountsByUserIdAndAccountType(@RequestBody AccountRequest request) {
        return this.accountService.getAccountsByUserIdAndType(request.getUserId(), request.getAccountType());
    }
}
