package com.ry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.entity.Comment;
import com.ry.domain.vo.CommentVo;
import com.ry.domain.vo.PageVo;
import com.ry.enums.AppHttpCodeEnum;
import com.ry.exception.SystemException;
import com.ry.mapper.CommentMapper;
import com.ry.service.CommentService;
import com.ry.service.UserService;
import com.ry.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 12:10:03
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        // 根评论 routId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOTID_ORIGINAL);
        // 按时间倒序排列
        queryWrapper.orderByDesc(Comment::getCreateTime);
        // 评论类型匹配
        queryWrapper.eq(Comment::getType,commentType);
        // 分页查询
        Page<Comment> page =new Page(pageNum,pageSize);
        page(page,queryWrapper);
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        // 进行封装返回
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        // stream流方式
        commentVoList.stream()
                .map(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())))
                .collect(Collectors.toList());
        // for循环的方式
//        for (CommentVo commentVo : commentVoList) {
//            // 查询对应的子评论
//            List<CommentVo> children = getChildren(commentVo.getId());
//            // 赋值
//            commentVo.setChildren(children);
//        }
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    /**
     * 新增评论
     * @param comment 评论
     * @param token  token头
     * @return
     */
    @Override
    public ResponseResult addComment(Comment comment,String token) {
        // 必须包含token 即登录后才可以评论
        if(!StringUtils.hasText(token)){
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        // 评论不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 根据根评论id获得子评论
        queryWrapper.eq(Comment::getRootId,id);
        // 按时间倒序排列
        queryWrapper.orderByDesc(Comment::getCreateTime);
        // 进行封装返回
        List<Comment> comments = list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return  commentVos;
    }

    // 封装操作相关的方法 获取评论用户的昵称 和 所评论的目标用户的昵称 comment表中是没有的
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 使用stream流的方式
//        commentVos.stream()
//                .map(commentVo -> commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName()))
//                .filter(commentVo -> !commentVo.getToCommentUserId().equals(SystemConstants.COMMENT_TOCOMMENTUSERID_ORIGINAL))
//                .map(commentVo -> commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName()))
//                .collect(Collectors.toList());

        // for循环的方式
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }

        return commentVos;
    }
}
