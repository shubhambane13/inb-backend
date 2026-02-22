package com.netbank.inb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dropdown_elements")
@Data
public class DropdownElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dd_element_id", nullable = false)
    private String elementId; // e.g., "account_type"

    @Column(name = "dd_label", nullable = false)
    private String label; // e.g., "Current Account"

    @Column(name = "dd_value", nullable = false)
    private String value; // e.g., "CURRENT"

    @Column(name = "filter_1")
    private String filter1; // e.g., "C" (Corporate)

    @Column(name = "filter_2")
    private String filter2;

    @Column(name = "filter_3")
    private String filter3;
}