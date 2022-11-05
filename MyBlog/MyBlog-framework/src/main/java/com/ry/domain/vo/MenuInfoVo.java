package com.ry.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoVo {
    //菜单ID
    private Long id;
    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //是否为外链（1是 0否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（1显示 0隐藏）
    private String visible;
    //菜单状态（1正常 0停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;
}
