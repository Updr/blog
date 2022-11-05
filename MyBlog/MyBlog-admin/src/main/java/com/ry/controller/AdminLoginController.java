package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.entity.User;
import com.ry.enums.AppHttpCodeEnum;
import com.ry.exception.SystemException;
import com.ry.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminLoginController {
    @Autowired
    AdminLoginService adminLoginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        return adminLoginService.getInfo();
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        return adminLoginService.getRouters();
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }
}
