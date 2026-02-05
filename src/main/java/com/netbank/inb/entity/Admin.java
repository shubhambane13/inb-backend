package com.netbank.inb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@DiscriminatorValue("A")
@Data
public class Admin extends User {
    @Column(nullable = false, unique = true)
    private String adminId;      // Unique admin identifier
}