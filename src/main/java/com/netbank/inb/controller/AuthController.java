package com.netbank.inb.controller;

import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.LoginRequest;
import com.netbank.inb.dto.LoginResponse;
import com.netbank.inb.dto.UserDto;
import com.netbank.inb.exception.BadApiRequestException;
import com.netbank.inb.security.JwtHelper;
import com.netbank.inb.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register-customer")
    public ResponseEntity<ApiResponseMessage> registerCustomer(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createCustomerUser(userDto), HttpStatus.OK);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponseMessage> registerAdmin(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createAdminUser(userDto), HttpStatus.OK);
    }

    @PostMapping("/login-admin")
    public ResponseEntity<LoginResponse> loginAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        this.doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String token = jwtHelper.generateToken(userDetails);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        LoginResponse loginResponse = LoginResponse.builder().jwtToken(token).user(userDto).build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/login-customer")
    public ResponseEntity<LoginResponse> loginCustomer(@Valid @RequestBody LoginRequest loginRequest) {
        this.doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String token = jwtHelper.generateToken(userDetails);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        LoginResponse loginResponse = LoginResponse.builder().jwtToken(token).user(userDto).build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    private Boolean doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
            return true;
        } catch (BadCredentialsException e) {
            throw new BadApiRequestException("Invalid username or password");
        }
    }
}
