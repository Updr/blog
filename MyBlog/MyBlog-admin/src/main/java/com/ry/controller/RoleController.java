package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.dto.*;
import com.ry.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto){
        return roleService.roleList(pageNum,pageSize,roleListDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatusDto){
        return roleService.changeStatus(changeStatusDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return roleService.getRoleById(id);
    }

    @PostMapping()
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.addRole(addRoleDto);
    }

    @PutMapping()
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto){
        return roleService.updateRole(updateRoleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        return roleService.deleteRole(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

    @DeleteMapping("/batchRemove")
    public ResponseResult removeRoles(@RequestBody List<Long> ids){
        return roleService.removeRoles(ids);
    }
}
