package com.ry.service.impl;

import com.ry.constants.SystemConstants;
import com.ry.domain.ResponseResult;
import com.ry.domain.entity.LoginUser;
import com.ry.domain.entity.User;
import com.ry.domain.vo.AdminUserInfoVo;
import com.ry.domain.vo.MenuVo;
import com.ry.domain.vo.RoutersVo;
import com.ry.domain.vo.UserInfoVo;
import com.ry.service.AdminLoginService;
import com.ry.service.MenuService;
import com.ry.service.RoleService;
import com.ry.utils.BeanCopyUtils;
import com.ry.utils.JwtUtil;
import com.ry.utils.RedisCache;
import com.ry.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.REDIS_USER_KEY +userId,loginUser);

        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult getInfo() {
        // 获取当前登录的用户
        User user = SecurityUtils.getLoginUser().getUser();
        Long id = SecurityUtils.getUserId();
        // 根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(id);
        // 根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(id);
        // 获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @Override
    public ResponseResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        // 查询menu 结果是tree的形式
        List<MenuVo> menuVos = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menuVos));
    }

    @Override
    public ResponseResult logout() {
        // 获取登录用户的userId
        Long useId = SecurityUtils.getUserId();
        // 删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.REDIS_USER_KEY+useId);
        return ResponseResult.okResult();
    }
}
