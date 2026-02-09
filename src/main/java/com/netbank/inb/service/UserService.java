package com.netbank.inb.service;

import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.UserDto;

public interface UserService {
    // create
    ApiResponseMessage createCustomerUser(UserDto userDto);

    ApiResponseMessage createAdminUser(UserDto userDto);

    PageableResponse<UserDto> getActiveCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<UserDto> getPendingCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<UserDto> getLockedCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
}
