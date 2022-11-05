package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.TagListDto;
import com.ry.domain.entity.Tag;

import java.util.List;


/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2022-10-17 10:55:42
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult getTagById(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult removeTags(List<Long> ids);

    ResponseResult listAllTag();
}
