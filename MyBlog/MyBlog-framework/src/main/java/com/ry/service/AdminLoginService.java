package com.ry.service;

import com.ry.domain.ResponseResult;
import com.ry.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult getInfo();

    ResponseResult getRouters();

    ResponseResult logout();
}
