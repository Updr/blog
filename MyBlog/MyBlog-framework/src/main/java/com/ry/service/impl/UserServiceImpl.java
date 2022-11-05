package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.*;
import com.ry.domain.entity.Link;
import com.ry.domain.entity.Role;
import com.ry.domain.entity.User;
import com.ry.domain.entity.UserRole;
import com.ry.domain.vo.*;
import com.ry.enums.AppHttpCodeEnum;
import com.ry.exception.SystemException;
import com.ry.mapper.RoleMapper;
import com.ry.mapper.UserMapper;
import com.ry.service.RoleService;
import com.ry.service.UserRoleService;
import com.ry.service.UserService;
import com.ry.utils.BeanCopyUtils;
import com.ry.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 13:57:47
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",user.getId());
        updateWrapper.set("avatar",user.getAvatar());
        updateWrapper.set("nick_name",user.getNickName());
        updateWrapper.set("email",user.getEmail());
        updateWrapper.set("sex",user.getSex());
        userService.update(user,updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        // 对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        // 存入数据库
        user.setPassword(encodePassword);
        save(user);
        return ResponseResult.okResult();
    }

    /**
     * 后台-系统管理-用户管理-查询用户列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param userListDto 查询参数
     * @return
     */
    @Override
    public ResponseResult userList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        // 可以根据用户名模糊搜索 可以进行手机号的搜索 可以进行状态的查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(userListDto.getStatus()),User::getStatus,userListDto.getStatus());
        queryWrapper.eq(StringUtils.hasText(userListDto.getPhonenumber()),User::getPhonenumber,userListDto.getPhonenumber());
        queryWrapper.like(StringUtils.hasText(userListDto.getUserName()),User::getUserName,userListDto.getUserName());
        // 进行分页
        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        // 封装返回数据
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserVo.class);
        PageVo pageVo = new PageVo(userVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(ChangeStatusDto changeStatusDto) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,changeStatusDto.getId());
        updateWrapper.set(User::getStatus,changeStatusDto.getStatus());
        update(getById(changeStatusDto.getId()),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-用户管理-新增角色
     * @param addUserDto 新增角色dto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addUser(AddUserDto addUserDto) {
        // 用户名不能为空
        if(!StringUtils.hasText(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        // 对数据进行是否存在的判断
        if(userNameExist(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(addUserDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExist(addUserDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 对密码进行加密后将用户信息存入User表
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        String passwordencode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordencode);
        save(user);
        // 将用户和角色的对应信息存入UserRole表
        List<UserRole> userRoles = addUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    /**
     * 后台-用户管理-修改用户信息-根据id获取用户相关信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getUserById(Long id) {
        // 获取用户信息
        User user = getById(id);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        // 获取用户所关联的角色id列表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoleList.stream()
                .map(userRole -> userRole.getRoleId())
                .collect(Collectors.toList());
        // 获取所有角色列表
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL);
        List<Role> roleList = roleService.list(roleLambdaQueryWrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roleList, RoleVo.class);
        // 封装返回
        UserManagementVo userManagementVo = new UserManagementVo(userVo,roleIds,roleVos);
        return ResponseResult.okResult(userManagementVo);
    }

    /**
     * 后台-用户管理-修改用户信息
     * @param updateUserDto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        // 更新User表信息
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,updateUserDto.getId());
        updateWrapper.set(User::getUserName,updateUserDto.getUserName());
        updateWrapper.set(User::getNickName,updateUserDto.getNickName());
        updateWrapper.set(User::getEmail,updateUserDto.getEmail());
        updateWrapper.set(User::getPhonenumber,updateUserDto.getPhonenumber());
        updateWrapper.set(User::getStatus,updateUserDto.getStatus());
        updateWrapper.set(User::getSex,updateUserDto.getSex());
        update(getById(updateUserDto.getId()),updateWrapper);
        // 更新UserRole表信息
        // 先删除关联信息，再添加新的关联信息
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,updateUserDto.getId());
        userRoleService.remove(queryWrapper);
        List<UserRole> userRoles = updateUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(updateUserDto.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    /**
     * 后台-用户管理-根据id删除用户
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteUser(Long id) {
        if(id.equals(SecurityUtils.getUserId())){
            throw new RuntimeException("不能删除当前操作的用户");
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,id);
        updateWrapper.set(User::getDelFlag,SystemConstants.DELETE_YES);
        update(getById(id),updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeUsers(List<Long> ids) {
        ids.forEach(id ->{
            LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(User::getId,id)
                    .set(User::getDelFlag,SystemConstants.DELETE_YES);
            update(getById(id),updateWrapper);
        });
        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }


    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }
}
