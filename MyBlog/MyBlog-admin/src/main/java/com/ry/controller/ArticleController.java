package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddArticleDto;
import com.ry.domain.dto.ArticleListDto;
import com.ry.domain.dto.UpdateArticleDto;
import com.ry.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.pageArticleList(pageNum,pageSize,articleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticleById(@PathVariable("id") Long id){
        return  articleService.deleteArticleById(id);
    }

    @DeleteMapping("/batchRemove")
    public ResponseResult removeArticles(@RequestBody List<Long> ids){
        return articleService.removeArticles(ids);
    }
}
