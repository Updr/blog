package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 获取分类的列表
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }

    // 获取分类总数
    @GetMapping("/categoryTotal")
    public ResponseResult getCategoryTotal(){
        int count = categoryService.count();
        return ResponseResult.okResult(count);
    }
}
