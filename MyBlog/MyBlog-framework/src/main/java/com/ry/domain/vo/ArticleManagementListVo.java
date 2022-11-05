package com.ry.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleManagementListVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
