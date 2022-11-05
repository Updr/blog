package com.ry.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {
    //菜单ID
    private Long id;
    //父菜单ID
    private Long parentId;
    //菜单名称
    private String label;
    //子菜单
    private List<MenuTreeVo> children;
}
