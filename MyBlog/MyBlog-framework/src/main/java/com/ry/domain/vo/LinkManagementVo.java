package com.ry.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkManagementVo {
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
}
