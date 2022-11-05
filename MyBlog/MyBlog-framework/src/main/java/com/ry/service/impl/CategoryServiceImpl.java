package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddCategoryDto;
import com.ry.domain.dto.CategoryListDto;
import com.ry.domain.dto.UpdateCategoryDto;
import com.ry.domain.entity.Article;
import com.ry.domain.entity.Category;
import com.ry.domain.vo.CategoryListVo;
import com.ry.domain.vo.CategoryManagementVo;
import com.ry.domain.vo.CategoryVo;
import com.ry.domain.vo.PageVo;
import com.ry.mapper.CategoryMapper;
import com.ry.service.ArticleService;
import com.ry.service.CategoryService;
import com.ry.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2022-10-04 21:56:45
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    /**
     * 前台-获取分类列表
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表 状态为已发布的文章 （分类中必须包含已发布的文章）
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        // 获取文章的分类id，并且去重 （需要根据id查询分类）
        Set<Long> categoryIds= articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        // 查询分类表 状态为正常的分类
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装vo
        List<CategoryVo> categoryVos= BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    /**
     * 后台-分类管理-获取分类列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param categoryListDto 分类列表Dto
     * @return
     */
    @Override
    public ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        // 能根据状态进行查询 能根据分类名称进行模糊查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getStatus()),Category::getStatus,categoryListDto.getStatus());
        queryWrapper.like(StringUtils.hasText(categoryListDto.getName()),Category::getName,categoryListDto.getName());
        // 进行分页处理
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryListVo.class);
        PageVo pageVo = new PageVo(categoryListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 后台-分类管理-新增分类
     * @param addCategoryDto
     * @return
     */
    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    /**
     * 后台-分类管理-根据id获取分类信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        CategoryManagementVo categoryManagementVo = BeanCopyUtils.copyBean(category, CategoryManagementVo.class);
        return ResponseResult.okResult(categoryManagementVo);
    }

    /**
     * 后台-分类管理-根据id更新分类
     * @param updateCategoryDto
     * @return
     */
    @Override
    public ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Category::getId,updateCategoryDto.getId());
        updateWrapper.set(Category::getName,updateCategoryDto.getName());
        updateWrapper.set(Category::getStatus,updateCategoryDto.getStatus());
        updateWrapper.set(Category::getDescription,updateCategoryDto.getDescription());
        update(getById(updateCategoryDto.getId()),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-分类管理-根据id删除分类
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteCategory(Long id) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Category::getId,id);
        updateWrapper.set(Category::getDelFlag,SystemConstants.DELETE_YES);
        update(getById(id),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-分类管理-批量删除分类
     * @param ids
     * @return
     */
    @Override
    public ResponseResult removeCategories(List<Long> ids) {
        ids.forEach(id ->{
            LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Category::getId,id)
                    .set(Category::getDelFlag,SystemConstants.DELETE_YES);
            update(getById(id),updateWrapper);
        });
        return ResponseResult.okResult();
    }
}
