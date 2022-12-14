package com.ry.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddCategoryDto;
import com.ry.domain.dto.CategoryListDto;
import com.ry.domain.dto.TagListDto;
import com.ry.domain.dto.UpdateCategoryDto;
import com.ry.domain.entity.Category;
import com.ry.domain.vo.ExcelCategoryVo;
import com.ry.enums.AppHttpCodeEnum;
import com.ry.service.CategoryService;
import com.ry.utils.BeanCopyUtils;
import com.ry.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @GetMapping("/list")
    public ResponseResult pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto){
        return categoryService.pageCategoryList(pageNum,pageSize,categoryListDto);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto){
        return categoryService.addCategory(addCategoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto){
        return categoryService.updateCategory(updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }

    @DeleteMapping("/batchRemove")
    public ResponseResult removeCategories(@RequestBody List<Long> ids){
        return categoryService.removeCategories(ids);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            // ??????????????????????????????
            WebUtils.setDownLoadHeader("??????.xlsx",response);
            // ???????????????????????????
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
            // ??????????????????Excel???
            EasyExcel.write(response.getOutputStream(),ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("????????????")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            // ????????????????????????json
//            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
            e.printStackTrace();
        }
    }
}
