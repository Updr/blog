package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddCategoryDto;
import com.ry.domain.dto.CategoryListDto;
import com.ry.domain.dto.UpdateCategoryDto;
import com.ry.domain.entity.Category;

import java.util.List;


/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2022-10-04 21:56:45
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult addCategory(AddCategoryDto addCategoryDto);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto);

    ResponseResult deleteCategory(Long id);

    ResponseResult removeCategories(List<Long> ids);
}
