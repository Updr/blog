package com.ry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ry.domain.entity.Menu;

import java.util.List;


/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-18 15:58:59
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
