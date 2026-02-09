package com.netbank.inb.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PagingRequest {
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sortBy = "id";
    private String sortDir = "asc";
}
