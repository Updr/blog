package com.ry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.domain.entity.ArticleTag;
import com.ry.mapper.ArticleTagMapper;
import com.ry.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * (ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-21 10:36:51
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
