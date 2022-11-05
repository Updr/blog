package com.ry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ry.domain.ResponseResult;
import com.ry.domain.dto.*;
import com.ry.domain.entity.User;

import java.util.List;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-10-09 13:57:47
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult userList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult changeStatus(ChangeStatusDto changeStatusDto);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(UpdateUserDto updateUserDto);

    ResponseResult deleteUser(Long id);

    ResponseResult removeUsers(List<Long> ids);
}
