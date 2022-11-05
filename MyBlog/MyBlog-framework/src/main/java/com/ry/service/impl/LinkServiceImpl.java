package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.AddLinkDto;
import com.ry.domain.dto.LinkListDto;
import com.ry.domain.dto.UpdateLinkDto;
import com.ry.domain.entity.Category;
import com.ry.domain.entity.Link;
import com.ry.domain.vo.LinkListVo;
import com.ry.domain.vo.LinkManagementVo;
import com.ry.domain.vo.LinkVo;
import com.ry.domain.vo.PageVo;
import com.ry.mapper.LinkMapper;
import com.ry.service.LinkService;
import com.ry.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * (Link)表服务实现类
 *
 * @author makejava
 * @since 2022-10-06 09:47:59
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    // 获取友链
    public ResponseResult getAllLink() {
        // 获取已经审核通过的友链
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(lambdaQueryWrapper);
        // 封装为vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    /**
     * 后台-友链管理-获取友链列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param linkListDto 友链查询参数
     * @return
     */
    @Override
    public ResponseResult pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        // 	能根据友链名称进行模糊查询 能根据状态进行查询。
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(linkListDto.getStatus()),Link::getStatus,linkListDto.getStatus());
        queryWrapper.like(StringUtils.hasText(linkListDto.getName()),Link::getName,linkListDto.getName());
        // 分页
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        // 封装返回
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkListVo.class);
        PageVo pageVo = new PageVo(linkListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 后台-友链管理-新增友链
     * @param addLinkDto
     * @return
     */
    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    /**
     * 后台-友链管理-修改友链-根据id查询友链
     * @param id
     * @return
     */
    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        LinkManagementVo linkManagementVo = BeanCopyUtils.copyBean(link, LinkManagementVo.class);
        return ResponseResult.okResult(linkManagementVo);
    }

    /**
     * 后台-友链管理-修改友链
     * @param updateLinkDto
     * @return
     */
    @Override
    public ResponseResult updateLink(UpdateLinkDto updateLinkDto) {
        LambdaUpdateWrapper<Link> updateWrapper =new LambdaUpdateWrapper<>();
        updateWrapper.eq(Link::getId,updateLinkDto.getId())
                .set(Link::getName,updateLinkDto.getName())
                .set(Link::getAddress,updateLinkDto.getAddress())
                .set(Link::getDescription,updateLinkDto.getDescription())
                .set(Link::getLogo,updateLinkDto.getLogo())
                .set(Link::getStatus,updateLinkDto.getStatus());
        update(getById(updateLinkDto.getId()),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-友链管理-根据id删除友链
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteLink(Long id) {
        LambdaUpdateWrapper<Link> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Link::getId,id)
                .set(Link::getDelFlag,SystemConstants.DELETE_YES);
        update(getById(id),updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 后台-友链管理-批量删除友链
     * @param ids
     * @return
     */
    @Override
    public ResponseResult removeLinks(List<Long> ids) {
        ids.forEach(id ->{
            LambdaUpdateWrapper<Link> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Link::getId,id)
                    .set(Link::getDelFlag,SystemConstants.DELETE_YES);
            update(getById(id),updateWrapper);
        });
        return ResponseResult.okResult();
    }
}
