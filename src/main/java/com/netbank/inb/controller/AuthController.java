package com.netbank.inb.controller;

import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.LoginRequest;
import com.netbank.inb.dto.LoginResponse;
import com.netbank.inb.dto.UserDto;
import com.netbank.inb.entity.User;
import com.netbank.inb.exception.BadApiRequestException;
import com.netbank.inb.repository.UserRepository;
import com.netbank.inb.security.JwtHelper;
import com.netbank.inb.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new BadApiRequestException("Customer cannot login from Admin Portal");
        }
        String token = jwtHelper.generateToken(userDetails);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        LoginResponse loginResponse = LoginResponse.builder().jwtToken(token).user(userDto).status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/login-customer")
    public ResponseEntity<LoginResponse> loginCustomer(@Valid @RequestBody LoginRequest loginRequest) {
        this.doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        boolean isCustomer = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_NORMAL"));

        if (!isCustomer) {
            throw new BadApiRequestException("Admins cannot login from Customer Portal");
        }

        String token = jwtHelper.generateToken(userDetails);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        LoginResponse loginResponse = LoginResponse.builder().jwtToken(token).user(userDto).status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    private Boolean doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {

            manager.authenticate(authentication);

            this.userRepository.findByEmail(email).ifPresent(user -> {
                if (user.getFailedLoginAttempts() > 0) {
                    user.setFailedLoginAttempts(0);
                    this.userRepository.save(user);
                }
            });

            return true;

        } catch (LockedException e) {

            throw new BadApiRequestException("Your account has been locked due to multiple failed attempts. Please contact Admin.");

        } catch (BadCredentialsException e) {
            User user = this.userRepository.findByEmail(email).orElse(null);

            if (user != null) {
                if (Boolean.TRUE.equals(user.getAccountLocked())) {
                    throw new BadApiRequestException("Your account is locked.");
                }

                int newFailCount = (user.getFailedLoginAttempts() == null ? 0 : user.getFailedLoginAttempts()) + 1;
                user.setFailedLoginAttempts(newFailCount);

                if (newFailCount >= 3) {
                    user.setAccountLocked(true);
                    this.userRepository.save(user);
                    throw new BadApiRequestException("Invalid Password. Account is now LOCKED due to 3 failed attempts.");
                }

                this.userRepository.save(user);
            }

            throw new BadApiRequestException("Invalid username or password");
        }
    }
}
