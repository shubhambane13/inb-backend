package com.netbank.inb.dto;

import com.netbank.inb.entity.Account;
import com.netbank.inb.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private Long id;

    @Size(min = 3, max = 20, message = "Invalid name!!")
    private String name;

    //    @Email(message = "Invalid User Email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid User Email")
    private String email;

    @Size(min = 8, message = "Invalid Password!!")
    private String password;

    @Size(min = 10, message = "Invalid Phone!!")
    private String phone;

    @Size(min = 15, message = "Invalid Address!!")
    private String address;

    private LocalDateTime registrationDate;

    private Boolean accountApproved = false;

    private Integer failedLoginAttempts = 0;

    private Boolean accountLocked = false;

    private List<Account> accounts;

    private Set<Role> roles = new HashSet<>();
}
