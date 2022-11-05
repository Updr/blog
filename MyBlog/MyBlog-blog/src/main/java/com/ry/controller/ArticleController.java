package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.entity.Article;
import com.ry.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//       return articleService.list();
//    }

    // 获取热门文章列表
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    // 获取文章列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    //查看文章详情
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    // 更新浏览量
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

    // 获取文章总数
    @GetMapping("/articleTotal")
    public ResponseResult getArticleTotal(){
        int count = articleService.count();
        return ResponseResult.okResult(count);
    }

    // 获取文章总浏览量
    @GetMapping("/viewCountTotal")
    public ResponseResult getViewCountTotal(){
        return articleService.getViewCountTotal();
    }

    // 获取文章目录索引
    // TODO 后续需要完善
    @GetMapping("articleIndex")
    public ResponseResult getArticleIndex(){
        return articleService.getArticleIndex();
    }
}
