package com.netbank.inb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDto {
    private Long activeCount;
    private Long pendingCount;
    private Long lockedCount;
}