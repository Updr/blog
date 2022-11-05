package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddRoleDto;
import com.ry.domain.dto.ChangeStatusDto;
import com.ry.domain.dto.RoleListDto;
import com.ry.domain.dto.UpdateRoleDto;
import com.ry.domain.entity.Menu;
import com.ry.domain.entity.Role;
import com.ry.domain.entity.RoleMenu;
import com.ry.domain.entity.User;
import com.ry.domain.vo.PageVo;
import com.ry.domain.vo.RoleListVo;
import com.ry.domain.vo.RoleVo;
import com.ry.mapper.RoleMapper;
import com.ry.service.RoleMenuService;
import com.ry.service.RoleService;
import com.ry.utils.BeanCopyUtils;
import com.ry.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-18 16:14:04
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员 如果是返回集合中只需要有admin
        if(SecurityUtils.isAdmin()){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        // 否则查询用户所具有的的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    /**
     * 后台-角色管理-查询角色列表
     * @param pageNum
     * @param pageSize
     * @param roleListDto
     * @return
     */
    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        // 	针对角色名称进行模糊查询 针对状态进行查询 按照role_sort进行升序排列
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(roleListDto.getStatus()),Role::getStatus,roleListDto.getStatus());
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()),Role::getRoleName,roleListDto.getRoleName());
        queryWrapper.orderByAsc(Role::getRoleSort);
        // 分页
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        // 封装返回
        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleListVo.class);
        PageVo pageVo = new PageVo(roleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 后台-角色管理-修改角色状态
     * @param changeStatusDto 角色状态Dto
     * @return
     */
    @Override
    public ResponseResult changeStatus(ChangeStatusDto changeStatusDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId,changeStatusDto.getId());
        updateWrapper.set(Role::getStatus,changeStatusDto.getStatus());
        update(getById(changeStatusDto.getId()),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-角色管理-修改角色-根据id获取角色信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    /**
     * 后台-角色管理-新增角色
     * @param addRoleDto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        // 新增角色信息
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        // 新增RoleMenu关联表信息
        List<RoleMenu> roleMenus = addRoleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    /**
     * 后台-角色管理-修改角色-更新角色信息
     * @param updateRoleDto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        // 更新Role表
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);
        // 先删除旧的RoleMenu表中的信息
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuService.remove(queryWrapper);
        // 再增加新的更新的RoleMenu表中的信息
        List<RoleMenu> roleMenus = updateRoleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    /**
     * 后台-角色管理-根据id删除角色
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteRole(Long id) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId,id);
        updateWrapper.set(Role::getDelFlag, SystemConstants.DELETE_YES);
        update(getById(id),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-用户管理-新增用户-获取角色列表
     * @return
     */
    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus,SystemConstants.ROLE_STATUS_NORMAL);
        List<Role> roleList = list(queryWrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roleList, RoleVo.class);
        return ResponseResult.okResult(roleVos);
    }

    /**
     * 后台-角色管理-批量删除角色
     * @param ids
     * @return
     */
    @Override
    public ResponseResult removeRoles(List<Long> ids) {
        ids.forEach(id ->{
            LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Role::getId,id)
                    .set(Role::getDelFlag,SystemConstants.DELETE_YES);
            update(getById(id),updateWrapper);
        });
        return ResponseResult.okResult();
    }
}
