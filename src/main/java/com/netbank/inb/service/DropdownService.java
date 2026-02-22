package com.netbank.inb.service;

import com.netbank.inb.cache.DropdownCacheManager;
import com.netbank.inb.dto.DropdownElementDto;
import com.netbank.inb.dto.DropdownRequestDto;
import com.netbank.inb.entity.DropdownElement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DropdownService {

    @Autowired
    private DropdownCacheManager cacheManager; // Inject your new custom cache

    @Autowired
    private ModelMapper modelMapper;

    public List<DropdownElementDto> getFilteredDropdowns(DropdownRequestDto request) {

        // 1. Get the list instantly from RAM (no database hit)
        List<DropdownElement> rawElements = cacheManager.getListByElementId(request.getElementId());

        // 2. Filter and Map
        return rawElements.stream()
                .filter(e -> request.getFilter1() == null || request.getFilter1().equals(e.getFilter1()))
                .filter(e -> request.getFilter2() == null || request.getFilter2().equals(e.getFilter2()))
                .filter(e -> request.getFilter3() == null || request.getFilter3().equals(e.getFilter3()))
                .map(e -> modelMapper.map(e, DropdownElementDto.class))
                .collect(Collectors.toList());
    }
}