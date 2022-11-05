package com.ry.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    //主键
    private Long id;
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //账号状态（1正常 0停用）
    private String status;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;

    //所拥有的的角色的id
    private List<Long> roleIds;
}
