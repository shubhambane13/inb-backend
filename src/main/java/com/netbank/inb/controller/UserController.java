package com.netbank.inb.controller;

import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.PagingRequest;
import com.netbank.inb.dto.UserDto;
import com.netbank.inb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Customer Management APIs

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

    @PostMapping("/approve-customer")
    public ResponseEntity<ApiResponseMessage> approveCustomer(@RequestBody UserDto user) {
         return new ResponseEntity<>(this.userService.approveCustomer(user.getId()), HttpStatus.OK);
    }

    @PostMapping("/reject-customer")
    public ResponseEntity<ApiResponseMessage> rejectCustomer(@RequestBody UserDto user) {
        return new ResponseEntity<>(this.userService.rejectCustomer(user.getId()), HttpStatus.OK);
    }

    @PostMapping("/lock-customer")
    public ResponseEntity<ApiResponseMessage> lockCustomer(@RequestBody UserDto user) {
        return new ResponseEntity<>(this.userService.lockCustomer(user.getId()), HttpStatus.OK);
    }

    @PostMapping("/unlock-customer")
    public ResponseEntity<ApiResponseMessage> unlockCustomer(@RequestBody UserDto user) {
        return new ResponseEntity<>(this.userService.unLockCustomer(user.getId()), HttpStatus.OK);
    }
}
