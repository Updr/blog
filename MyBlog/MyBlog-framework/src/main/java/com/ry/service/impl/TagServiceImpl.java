package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.TagListDto;
import com.ry.domain.entity.Tag;
import com.ry.domain.vo.PageVo;
import com.ry.domain.vo.TagVo;
import com.ry.mapper.TagMapper;
import com.ry.service.TagService;
import com.ry.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-17 10:55:42
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        // 封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Tag::getId,id);
        updateWrapper.set(Tag::getDelFlag, SystemConstants.DELETE_YES);
        update(getById(id),updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Tag::getId,tag.getId());
        updateWrapper.set(Tag::getName,tag.getName());
        updateWrapper.set(Tag::getRemark,tag.getRemark());
        update(tag,updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-标签管理-批量删除标签
     * @param ids 标签id集合
     * @return
     */
    @Override
    @Transactional
    public ResponseResult removeTags(List<Long> ids) {
        ids.forEach(id ->{
            LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Tag::getId,id)
                         .set(Tag::getDelFlag,SystemConstants.DELETE_YES);
            update(getById(id),updateWrapper);
        });
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}
