package com.ry.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListDto {
    //角色名称
    private String roleName;
    //角色状态（1正常 0停用）
    private String status;
}
