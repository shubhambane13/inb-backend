package com.netbank.inb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String customerId;      // Unique customer identifier

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private Boolean accountApproved = false;  // Registration approval status

    @Column
    private Integer failedLoginAttempts = 0;

    @Column
    private Boolean accountLocked = false;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;
}
