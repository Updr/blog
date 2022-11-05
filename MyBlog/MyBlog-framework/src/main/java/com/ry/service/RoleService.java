package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddRoleDto;
import com.ry.domain.dto.ChangeStatusDto;
import com.ry.domain.dto.RoleListDto;
import com.ry.domain.dto.UpdateRoleDto;
import com.ry.domain.entity.Role;

import java.util.List;


/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2022-10-18 16:14:04
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    ResponseResult changeStatus(ChangeStatusDto changeStatusDto);

    ResponseResult getRoleById(Long id);

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();

    ResponseResult removeRoles(List<Long> ids);
}
