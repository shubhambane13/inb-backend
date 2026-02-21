package com.netbank.inb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// This automatically converts "activeCount" -> "active_count" in JSON
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DashboardStatsDto {
    private Long activeCount;
    private Long pendingCount;
    private Long lockedCount;
}