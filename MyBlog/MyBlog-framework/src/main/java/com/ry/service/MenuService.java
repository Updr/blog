package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.MenuDto;
import com.ry.domain.dto.MenuListDto;
import com.ry.domain.entity.Menu;
import com.ry.domain.vo.MenuVo;

import java.util.List;


/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2022-10-18 15:58:59
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult menuList(MenuListDto menuListDto);


    ResponseResult addMenu(Menu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(MenuDto menuDto);

    ResponseResult deleteMenu(Long id);

    ResponseResult roleMenuTreeselect(Long roleId);

    ResponseResult treeselect();
}
