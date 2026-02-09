package com.netbank.inb.service.impl;

import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.UserDto;
import com.netbank.inb.entity.Role;
import com.netbank.inb.entity.User;
import com.netbank.inb.repository.RoleRepository;
import com.netbank.inb.repository.UserRepository;
import com.netbank.inb.service.UserService;
import com.netbank.inb.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Value("${admin.role.id}")
    private String adminRoleId;

    @Value("${normal.role.id}")
    private String normalRoleId;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ApiResponseMessage createCustomerUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setAccountApproved(false);
        userDto.setAccountLocked(false);
        userDto.setRegistrationDate(LocalDateTime.now());
        User user = mapper.map(userDto, User.class);
        Role role = this.roleRepository.findById(normalRoleId).orElseThrow(() -> new RuntimeException("User Not Found with given id"));
        user.getRoles().add(role);
        this.userRepository.save(user);
        ApiResponseMessage build = ApiResponseMessage.builder().message("User is in pending for approval queue.").status(HttpStatus.OK).success(true).build();
        return build;
    }

    @Override
    public ApiResponseMessage createAdminUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setAccountApproved(false);
        userDto.setAccountLocked(false);
        userDto.setRegistrationDate(LocalDateTime.now());
        User user = mapper.map(userDto, User.class);
        Role role = this.roleRepository.findById(adminRoleId).orElseThrow(() -> new RuntimeException("User Not Found with given id"));
        user.getRoles().add(role);
        this.userRepository.save(user);
        ApiResponseMessage build = ApiResponseMessage.builder().message("Admin is in pending for approval queue.").status(HttpStatus.OK).success(true).build();
        return build;
    }

    @Override
    public PageableResponse<UserDto> getActiveCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findByAccountApprovedTrueAndRolesId(normalRoleId, pageable);
        PageableResponse<UserDto> response = Util.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public PageableResponse<UserDto> getPendingCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findByAccountApprovedFalseAndRolesId(normalRoleId, pageable);
        PageableResponse<UserDto> response = Util.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public PageableResponse<UserDto> getLockedCustomerUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findByAccountLockedTrueAndRolesId(normalRoleId, pageable);
        PageableResponse<UserDto> response = Util.getPageableResponse(page, UserDto.class);
        return response;
    }
}
