package com.ry.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {
    private Long id;

    //友链网站名称
    private String name;
    //友链网站logo
    private String logo;
    //友链网站描述
    private String description;
    //友链网站地址
    private String address;

}
