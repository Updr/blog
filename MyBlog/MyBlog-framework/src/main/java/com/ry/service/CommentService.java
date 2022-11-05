package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.entity.Comment;


/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2022-10-09 12:10:03
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment,String token);
}
