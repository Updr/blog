package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.dto.MenuDto;
import com.ry.domain.dto.MenuListDto;
import com.ry.domain.entity.Menu;
import com.ry.domain.vo.MenuVo;
import com.ry.service.MenuService;
import com.ry.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult menuList(MenuListDto menuListDto){
        return menuService.menuList(menuListDto);
    }

    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    @PostMapping
    public ResponseResult addMenu(@RequestBody MenuDto menuDto){
        Menu menu = BeanCopyUtils.copyBean(menuDto, Menu.class);
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping()
    public ResponseResult updateMenu(@RequestBody MenuDto menuDto){
        return menuService.updateMenu(menuDto);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long id){
        return menuService.deleteMenu(id);
    }

    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeselect(@PathVariable("roleId") Long roleId){
        return menuService.roleMenuTreeselect(roleId);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeselect(){
        return menuService.treeselect();
    }

}
