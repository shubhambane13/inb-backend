package com.netbank.inb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("C")
@Data
public class Customer extends User {
    @Column(nullable = false, unique = true)
    private String customerId;      // Unique customer identifier

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;
}
