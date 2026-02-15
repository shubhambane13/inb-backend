package com.netbank.inb.repository;

import com.netbank.inb.dto.DashboardStatsDto;
import com.netbank.inb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
        SELECT new com.netbank.inb.dto.DashboardStatsDto(
            COUNT(CASE WHEN u.accountApproved = true AND u.accountLocked = false THEN 1 END),
            COUNT(CASE WHEN u.accountApproved = false THEN 1 END),
            COUNT(CASE WHEN u.accountLocked = true THEN 1 END)
        )
        FROM User u 
        JOIN u.roles r 
        WHERE r.id = :id
    """)
    DashboardStatsDto getDashboardStats(@Param("id") String id);
}
