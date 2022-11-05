package com.ry.service;

import com.ry.domain.ResponseResult;
import com.ry.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
