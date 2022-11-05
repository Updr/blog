package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddLinkDto;
import com.ry.domain.dto.LinkListDto;
import com.ry.domain.dto.UpdateLinkDto;
import com.ry.domain.entity.Link;

import java.util.List;


/**
 * (Link)表服务接口
 *
 * @author makejava
 * @since 2022-10-06 09:47:59
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(UpdateLinkDto updateLinkDto);

    ResponseResult deleteLink(Long id);

    ResponseResult removeLinks(List<Long> ids);
}
