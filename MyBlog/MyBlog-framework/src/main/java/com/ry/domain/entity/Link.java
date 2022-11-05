package com.ry.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Link)表实体类
 *
 * @author makejava
 * @since 2022-10-06 09:47:57
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ry_link")
public class Link  {
    @TableId
    private Long id;

    //友链网站名称
    private String name;
    //友链网站logo
    private String logo;
    //友链网站描述
    private String description;
    //友链网站地址
    private String address;
    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}
