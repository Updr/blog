package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.MenuDto;
import com.ry.domain.dto.MenuListDto;
import com.ry.domain.entity.Menu;
import com.ry.domain.entity.RoleMenu;
import com.ry.domain.vo.MenuInfoVo;
import com.ry.domain.vo.MenuTreeVo;
import com.ry.domain.vo.MenuVo;
import com.ry.domain.vo.RoleMenuTreeVo;
import com.ry.enums.AppHttpCodeEnum;
import com.ry.mapper.MenuMapper;
import com.ry.mapper.RoleMenuMapper;
import com.ry.service.MenuService;
import com.ry.service.RoleMenuService;
import com.ry.utils.BeanCopyUtils;
import com.ry.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-18 15:58:59
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员返回所有权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            // 获取所有菜单类型为菜单、按钮 不包含目录
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_M,SystemConstants.MENU_F);
            // 菜单是正常状态
            wrapper.eq(Menu::getStatus,SystemConstants.MENU_STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        // 否则返回当前用户所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 如果是否是管理员
        if(SecurityUtils.isAdmin()){
            // 如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 否则 获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        // 构建tree
        List<MenuVo> menuVoTree = builderMenuTree(menuVos,SystemConstants.MENU_PARENTID);
        return menuVoTree;
    }

    /**
     * 后台-菜单管理-查询菜单列表
     * @param menuListDto 查询参数
     * @return
     */
    @Override
    public ResponseResult menuList(MenuListDto menuListDto) {
        // 根据查询参数进行查询
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(menuListDto.getStatus()),Menu::getStatus,menuListDto.getStatus());
        queryWrapper.like(StringUtils.hasText(menuListDto.getMenuName()),Menu::getMenuName,menuListDto.getMenuName());
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    /**
     * 后台-菜单管理-新增菜单
     * @param menu 菜单
     * @return
     */
    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 后台-菜单管理-修改菜单-根据id获取菜单
     * @param id 菜单id
     * @return
     */
    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuInfoVo menuInfoVo = BeanCopyUtils.copyBean(menu, MenuInfoVo.class);
        return ResponseResult.okResult(menuInfoVo);
    }

    /**
     * 后台-菜单管理-修改菜单-根据id更新菜单
     * @param menuDto 传入菜单参数
     * @return
     */
    @Override
    public ResponseResult updateMenu(MenuDto menuDto) {
        // 如果把父菜单设置为当前菜单 报错返回
        Menu m = getById(menuDto.getId());
        if(m.getId().equals(menuDto.getParentId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"修改菜单'写博文'失败，上级菜单不能选择自己");
        }
        // 更新菜单
        Menu menu = BeanCopyUtils.copyBean(menuDto, Menu.class);
        updateById(menu);
        return ResponseResult.okResult();
    }

    /**
     * 后台-菜单管理-删除菜单-根据id删除菜单
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteMenu(Long id) {
        LambdaUpdateWrapper<Menu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Menu::getId,id);
        updateWrapper.set(Menu::getDelFlag,SystemConstants.DELETE_YES);
        update(getById(id),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-角色管理-加载对应角色菜单列表树接口
     * @return
     */
    @Override
    public ResponseResult roleMenuTreeselect(Long roleId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus,SystemConstants.MENU_STATUS_NORMAL);
        List<Menu> menuList = list(queryWrapper);
        // 调用构建树函数构建菜单树
        List<MenuTreeVo> menuTreeVoList = builderMenuTreeVoTree(menuList, SystemConstants.MENU_PARENTID);
        // 获取角色所关联的菜单权限id列表
        List<Long> checkedKeys = new ArrayList<>();
        if(roleId == 1L){
           menuList.forEach(menu -> checkedKeys.add(menu.getId()));
        } else {
            LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RoleMenu::getRoleId,roleId);
            List<RoleMenu> roleMenus = roleMenuService.list(wrapper);
            roleMenus.forEach(roleMenu -> checkedKeys.add(roleMenu.getMenuId()));
        }
        // 封装返回
        RoleMenuTreeVo roleMenuTreeVo = new RoleMenuTreeVo(menuTreeVoList,checkedKeys);
        return ResponseResult.okResult(roleMenuTreeVo);
    }

    /**
     * 后台-角色管理-获取菜单树
     * @return
     */
    @Override
    public ResponseResult treeselect() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus,SystemConstants.MENU_STATUS_NORMAL);
        List<Menu> menuList = list(queryWrapper);
        List<MenuTreeVo> menuTreeVos = builderMenuTreeVoTree(menuList, SystemConstants.MENU_PARENTID);
        return ResponseResult.okResult(menuTreeVos);
    }

    /**
     * 构建 MenuVo tree
     * @param menuVos
     * @param parentId
     * @return
     */
    private List<MenuVo> builderMenuTree(List<MenuVo> menuVos, Long parentId) {
        // 先找出第一层菜单 然后去找他们的子菜单设置到children属性中
        List<MenuVo> menuVoTree = menuVos.stream()
                .filter(menuVo -> menuVo.getParentId().equals(parentId))
                .map(menuVo -> menuVo.setChildren(getChildren(menuVo, menuVos)))
                .collect(Collectors.toList());
        return menuVoTree;
    }

    /**
     * 获取传入参数的 子MenuVo集合
     * @param menuVo
     * @param menuVos
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menuVo,List<MenuVo> menuVos){
        // 根据父id获取子菜单 递归获取三级菜单
        List<MenuVo> childrenList = menuVos.stream()
                .filter(m -> m.getParentId().equals(menuVo.getId()))
                .map(m -> m.setChildren(getChildren(m,menuVos)))
                .collect(Collectors.toList());
        return childrenList;
    }

    /**
     * 获取传入参数的 子MenuTreeVo集合
     * @param menuTreeVo
     * @param menuTreeVos
     * @return
     */
    private List<MenuTreeVo> getMenuTreeVoChildren(MenuTreeVo menuTreeVo,List<MenuTreeVo> menuTreeVos){
        // 根据父id获取子菜单 递归获取三级菜单
        List<MenuTreeVo> childrenList = menuTreeVos.stream()
                .filter(m -> m.getParentId().equals(menuTreeVo.getId()))
                .map(m -> m.setChildren(getMenuTreeVoChildren(m,menuTreeVos)))
                .collect(Collectors.toList());
        return childrenList;
    }

    /**
     * 构建 MenuTreeVo tree
     * @param menuList
     * @param parentId
     * @return
     */
    private List<MenuTreeVo> builderMenuTreeVoTree(List<Menu> menuList,Long parentId){
        // 批量设置label属性
        List<MenuTreeVo> menuTreeVos = BeanCopyUtils.copyBeanList(menuList, MenuTreeVo.class);
        menuTreeVos.forEach(menuTreeVo -> menuTreeVo.setLabel(getById(menuTreeVo.getId()).getMenuName()));
        // 构建菜单树
        List<MenuTreeVo> menuTreeVoList = menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(parentId))
                .map(menuTreeVo -> menuTreeVo.setChildren(getMenuTreeVoChildren(menuTreeVo, menuTreeVos)))
                .collect(Collectors.toList());
        return menuTreeVoList;
    }

}
