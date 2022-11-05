package com.ry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.domain.entity.RoleMenu;
import com.ry.mapper.RoleMenuMapper;
import com.ry.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * (RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-24 15:52:56
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
