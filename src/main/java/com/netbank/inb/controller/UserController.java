package com.netbank.inb.controller;

import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.PagingRequest;
import com.netbank.inb.dto.UserDto;
import com.netbank.inb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/active-customers")
    public ResponseEntity<PageableResponse<UserDto>> getActiveCustomers(@RequestBody PagingRequest request) {
        return new ResponseEntity<>(this.userService.getActiveCustomerUsers(request.getPageNumber(), request.getPageSize(), request.getSortBy(), request.getSortDir()), HttpStatus.OK);
    }

    @PostMapping("/pending-customers")
    public ResponseEntity<PageableResponse<UserDto>> getPendingCustomers(@RequestBody PagingRequest request) {
        return new ResponseEntity<>(this.userService.getPendingCustomerUsers(request.getPageNumber(), request.getPageSize(), request.getSortBy(), request.getSortDir()), HttpStatus.OK);
    }

    @PostMapping("/locked-customers")
    public ResponseEntity<PageableResponse<UserDto>> getLockedCustomers(@RequestBody PagingRequest request) {
        return new ResponseEntity<>(this.userService.getLockedCustomerUsers(request.getPageNumber(), request.getPageSize(), request.getSortBy(), request.getSortDir()), HttpStatus.OK);
    }
}
