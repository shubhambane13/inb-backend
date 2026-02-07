package com.netbank.inb.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginResponse {
    private String jwtToken;
    private UserDto user;
}
