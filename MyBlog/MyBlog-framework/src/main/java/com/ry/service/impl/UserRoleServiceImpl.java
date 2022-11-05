package com.ry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.domain.entity.UserRole;
import com.ry.mapper.UserRoleMapper;
import com.ry.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * (UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-10-26 10:59:38
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
