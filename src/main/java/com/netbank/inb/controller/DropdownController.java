package com.netbank.inb.controller;

import com.netbank.inb.dto.DropdownElementDto;
import com.netbank.inb.dto.DropdownRequestDto;
import com.netbank.inb.service.DropdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dropdowns")
public class DropdownController {

    @Autowired
    private DropdownService dropdownService;

    @PostMapping("/fetch")
    public List<DropdownElementDto> fetchDropdowns(@RequestBody DropdownRequestDto request) {
        return dropdownService.getFilteredDropdowns(request);
    }
}