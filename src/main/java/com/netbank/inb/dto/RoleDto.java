package com.netbank.inb.dto;

import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleDto {
    private String roleId;

    private String roleName;
}
