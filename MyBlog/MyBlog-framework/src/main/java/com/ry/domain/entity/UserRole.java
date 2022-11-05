package com.ry.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (UserRole)表实体类
 *
 * @author makejava
 * @since 2022-10-26 10:59:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole  {
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;




}
