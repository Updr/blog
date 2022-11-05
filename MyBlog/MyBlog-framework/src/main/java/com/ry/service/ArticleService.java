package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddArticleDto;
import com.ry.domain.dto.ArticleListDto;
import com.ry.domain.dto.UpdateArticleDto;
import com.ry.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult getArticleById(Long id);

    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticleById(Long id);

    ResponseResult getViewCountTotal();

    ResponseResult getArticleIndex();

    ResponseResult removeArticles(List<Long> ids);
}
