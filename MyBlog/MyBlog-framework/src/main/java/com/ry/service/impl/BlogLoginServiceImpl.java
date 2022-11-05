package com.ry.service.impl;

import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.entity.LoginUser;
import com.ry.domain.entity.User;
import com.ry.domain.vo.BlogUserLoginVo;
import com.ry.domain.vo.UserInfoVo;
import com.ry.service.BlogLoginService;
import com.ry.utils.BeanCopyUtils;
import com.ry.utils.JwtUtil;
import com.ry.utils.RedisCache;
import com.ry.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名和密码错误");
        }
        // 获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.REDIS_USERINFO_KEY +userId,loginUser);
        // 把token和userinfo封装 返回
        // 把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(),UserInfoVo.class);
        BlogUserLoginVo vo =new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        // 不使用工具类
//        // 获取token 解析获取userid
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser =(LoginUser) authentication.getPrincipal();
//        // 获取userid
//        Long userId = loginUser.getUser().getId();
        // 使用工具类
        Long userId = SecurityUtils.getUserId();
        // 删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.REDIS_USERINFO_KEY+userId);
        return ResponseResult.okResult();
    }
}
