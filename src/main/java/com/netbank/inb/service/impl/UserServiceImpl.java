package com.netbank.inb.service.impl;

import com.netbank.inb.constant.AccountConstant;
import com.netbank.inb.dto.ApiResponseMessage;
import com.netbank.inb.dto.DashboardStatsDto;
import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.UserDto;
import com.netbank.inb.entity.CurrentAccount;
import com.netbank.inb.entity.Role;
import com.netbank.inb.entity.SavingsAccount;
import com.netbank.inb.entity.User;
import com.netbank.inb.repository.CurrentAccountRepository;
import com.netbank.inb.repository.RoleRepository;
import com.netbank.inb.repository.SavingsAccountRepository;
import com.netbank.inb.repository.UserRepository;
import com.netbank.inb.service.EmailService;
import com.netbank.inb.service.UserService;
import com.netbank.inb.util.EmailUtil;
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

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private EmailService emailService;

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
        // async email sending
        emailService.sendHtmlEmail(user.getEmail(), "iNetBank: Registration Received", EmailUtil.getRegistrationPendingHtml(user.getName()));
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
        Page<User> page = userRepository.findByAccountApprovedTrueAndAccountLockedFalseAndRolesId(normalRoleId, pageable);
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

    @Override
    public ApiResponseMessage approveCustomer(Long customerId) {
        User user = this.userRepository.findByIdAndRolesId(customerId, normalRoleId).orElseThrow(() -> new RuntimeException("User Not Found with given id"));

        if(user.getAccountApproved()) {
            ApiResponseMessage build = ApiResponseMessage.builder().message("Customer is already approved.").status(HttpStatus.BAD_REQUEST).success(false).build();
            return build;
        }

        String accountNo = "";

        if (user.getRequestedAccountType().equalsIgnoreCase(AccountConstant.SAVING_ACCOUNT)) {
            SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.setCreatedDate(LocalDateTime.now());
            savingsAccount.setAccountNumber(Util.generateAccountNumber());
            savingsAccount.setCustomer(user);
            accountNo = savingsAccount.getAccountNumber();
            savingsAccountRepository.save(savingsAccount);
        } else {
            CurrentAccount currentAccount = new CurrentAccount();
            currentAccount.setCreatedDate(LocalDateTime.now());
            currentAccount.setAccountNumber(Util.generateAccountNumber()); // Utility method to make random 10 digits
            currentAccount.setCustomer(user); // Link to user
            accountNo = currentAccount.getAccountNumber();
            currentAccountRepository.save(currentAccount);
        }

        user.setAccountApproved(true);
        this.userRepository.save(user);
        ApiResponseMessage build = ApiResponseMessage.builder().message("Customer Approved Successfully.").status(HttpStatus.OK).success(true).build();
        emailService.sendHtmlEmail(user.getEmail(), "iNetBank: Account Approved - Welcome!", EmailUtil.getAccountApprovedHtml(user.getName(), accountNo));
        return build;
    }

    @Override
    public ApiResponseMessage rejectCustomer(Long customerId) {
        User user = this.userRepository.findByIdAndRolesId(customerId, normalRoleId).orElseThrow(() -> new RuntimeException("User Not Found with given id"));
        userRepository.delete(user);
        ApiResponseMessage build = ApiResponseMessage.builder().message("Customer Rejected Successfully.").status(HttpStatus.OK).success(true).build();
        emailService.sendHtmlEmail(user.getEmail(), "iNetBank: Account Rejected!", EmailUtil.getAccountRejectedHtml(user.getName(), null));
        return build;
    }

    @Override
    public ApiResponseMessage lockCustomer(Long customerId) {
        User user = this.userRepository.findByIdAndRolesId(customerId, normalRoleId).orElseThrow(() -> new RuntimeException("User Not Found with given id"));
        if(user.getAccountLocked()) {
            ApiResponseMessage build = ApiResponseMessage.builder().message("Customer is already locked.").status(HttpStatus.BAD_REQUEST).success(false).build();
            return build;
        }
        user.setAccountLocked(true);
        this.userRepository.save(user);
        ApiResponseMessage build = ApiResponseMessage.builder().message("Customer Locked Successfully.").status(HttpStatus.OK).success(true).build();
        emailService.sendHtmlEmail(user.getEmail(), "iNetBank: Account Locked!", EmailUtil.getAccountLockedHtml(user.getName()));
        return build;
    }

    @Override
    public ApiResponseMessage unLockCustomer(Long customerId) {
        User user = this.userRepository.findByIdAndRolesId(customerId, normalRoleId).orElseThrow(() -> new RuntimeException("User Not Found with given id"));
        if(!user.getAccountLocked()) {
            ApiResponseMessage build = ApiResponseMessage.builder().message("Customer is already unlocked.").status(HttpStatus.BAD_REQUEST).success(false).build();
            return build;
        }
        user.setAccountLocked(false);
        this.userRepository.save(user);
        ApiResponseMessage build = ApiResponseMessage.builder().message("Customer Unlocked Successfully.").status(HttpStatus.OK).success(true).build();
        emailService.sendHtmlEmail(user.getEmail(), "iNetBank: Account Unlocked!", EmailUtil.getAccountUnlockedHtml(user.getName()));
        return build;
    }

    @Override
    public DashboardStatsDto getDashboardStats() {
        return userRepository.getDashboardStats(normalRoleId);
    }
}
