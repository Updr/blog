package com.ry.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManagementVo {
    // 封装好的用户信息
    private UserVo user;
    // 用户所关联的角色id列表
    private List<Long> roleIds;
    // 获取用户所关联的角色列表
    private List<RoleVo> roles;
}
