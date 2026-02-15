package com.netbank.inb.repository;

import com.netbank.inb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String mail);
    // getActiveCustomers
    Page<User> findByAccountApprovedTrueAndAccountLockedFalseAndRolesId(String roleId, Pageable pageable);
    // getPendingCustomers
    Page<User> findByAccountApprovedFalseAndRolesId(String roleId, Pageable pageable);
    // getLockedCustomers
    Page<User> findByAccountLockedTrueAndRolesId(String roleId, Pageable pageable);
    // User by Role Id
    Optional<User> findByIdAndRolesId(Long id, String roleId);
}
