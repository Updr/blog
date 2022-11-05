package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ry.constants.SystemConstants;
import com.ry.domain.entity.LoginUser;
import com.ry.domain.entity.User;
import com.ry.mapper.MenuMapper;
import com.ry.mapper.RoleMapper;
import com.ry.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private MenuMapper menuMapper;

    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user =userMapper.selectOne(queryWrapper);
        // 判断是否查到用户 如果没查到抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        // 判断该角色是否被禁用
        if(roleMapper.getNormalStatusCount(user.getId()) == 0){
            throw new RuntimeException("该用户所有角色组均为禁用状态，请联系管理员");
        }
        //判断该用户是否被禁用
        if(user.getStatus().equals(SystemConstants.USER_STATUS_DRAFT)){
            throw new RuntimeException("该用户已被禁用，请联系管理员");
        }
        // 返回用户信息
        if(user.getType().equals(SystemConstants.ADMIN)){
            List<String> permissions = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,permissions);
        }
        return new LoginUser(user,null);
    }
}
