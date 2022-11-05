package com.ry.handler.security;

import com.alibaba.fastjson.JSON;
import com.ry.domain.ResponseResult;
import com.ry.enums.AppHttpCodeEnum;
import com.ry.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 授权失败处理器
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 打印错误信息
        accessDeniedException.printStackTrace();
        // 设置返回前端的错误信息
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        // 响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
