package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.dto.TagListDto;
import com.ry.domain.entity.Tag;
import com.ry.service.TagService;
import com.ry.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable("id") Long id){
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto){
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.updateTag(tag);
    }

    @DeleteMapping("/batchRemove")
    public ResponseResult removeTags(@RequestBody List<Long> ids){
        return tagService.removeTags(ids);
    }
}
