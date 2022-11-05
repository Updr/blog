package com.ry.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryDto {
    //分类名
    private String name;
    //描述
    private String description;
    //状态：1正常，0禁用
    private String status;
}
