package com.netbank.inb.service;

import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.UserDto;

public interface UserService {
    // create
    ApiResponseMessage createCustomerUser(UserDto userDto);

    ApiResponseMessage createAdminUser(UserDto userDto);



}
