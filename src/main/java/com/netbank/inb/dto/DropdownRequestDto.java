package com.netbank.inb.dto;

import lombok.Data;

@Data
public class DropdownRequestDto {
    private String elementId;
    private String filter1;
    private String filter2;
    private String filter3;
}