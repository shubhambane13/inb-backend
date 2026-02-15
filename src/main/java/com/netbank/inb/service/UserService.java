package com.netbank.inb.service;

import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.UserDto;

public interface UserService {
    // create
    ApiResponseMessage createCustomerUser(UserDto userDto);

    ApiResponseMessage createAdminUser(UserDto userDto);

    // Customer Management
    PageableResponse<UserDto> getActiveCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<UserDto> getPendingCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<UserDto> getLockedCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    ApiResponseMessage approveCustomer(Long customerId);

    ApiResponseMessage rejectCustomer(Long customerId);

    ApiResponseMessage lockCustomer(Long customerId);

    ApiResponseMessage unLockCustomer(Long customerId);
}
