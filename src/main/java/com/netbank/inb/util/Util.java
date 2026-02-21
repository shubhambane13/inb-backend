package com.netbank.inb.util;

import com.netbank.inb.dto.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static<U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> classType) {
        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, classType)).collect(Collectors.toList());
        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    public static String generateAccountNumber() {
        // Simple logic to generate 10 digit number
        return String.valueOf((long) (Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L));
    }
}
