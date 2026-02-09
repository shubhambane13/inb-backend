package com.netbank.inb.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginResponse {
    private HttpStatus status;
    private Boolean success;
    private String jwtToken;
    private UserDto user;
}
