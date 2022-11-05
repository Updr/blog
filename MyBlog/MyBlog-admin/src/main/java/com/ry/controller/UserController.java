package com.ry.controller;

import com.ry.domain.ResponseResult;
import com.ry.domain.dto.*;
import com.ry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseResult userList(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.userList(pageNum,pageSize,userListDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatusDto){
        return userService.changeStatus(changeStatusDto);
    }

    @PostMapping()
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        return userService.addUser(addUserDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PutMapping()
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") Long id){
        return userService.deleteUser(id);
    }

    @DeleteMapping("/batchRemove")
    public ResponseResult removeUsers(@RequestBody List<Long> ids){
        return userService.removeUsers(ids);
    }
}
