package com.ry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ry.domain.entity.Role;

import java.util.List;


/**
 * (Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-18 16:14:04
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    Integer getNormalStatusCount(Long userId);
}
