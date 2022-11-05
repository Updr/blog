package com.ry.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (RoleMenu)表实体类
 *
 * @author makejava
 * @since 2022-10-24 15:52:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu  {
    //角色ID
    private Long roleId;
    //菜单ID
    private Long menuId;

}
