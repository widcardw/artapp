package com.example.springdemo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.User;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 新增
     *
     * @return Result
     */
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        if (userService.existUserName(user.getUsername())) {
            return Result.error("-1", "用户名重复");
        }
        // 判断发送过来的密码是否为空
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        // 判断发送过来的昵称是否为空
        if (user.getNickName() == null) {
            user.setNickName("Default");
        }
        // 判断用户名是否合法
        if (!NameValidater.usernameValid(user.getUsername())) {
            return Result.usernameError();
        }
        // 判断昵称是否合法
        if (!NameValidater.nickNameValid(user.getNickName())) {
            return Result.nickNameError();
        }
        int i = userService.insertUser(user);
        return Result.success(i);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (one == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(one);
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        // 判断发送过来的密码是否为空
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        // 判断发送过来的昵称是否为空
        if (user.getNickName() == null) {
            user.setNickName("Default");
        }
        // 判断用户名是否合法
        if (!NameValidater.usernameValid(user.getUsername())) {
            return Result.usernameError();
        }
        // 判断昵称是否合法
        if (!NameValidater.nickNameValid(user.getNickName())) {
            return Result.nickNameError();
        }
        int i = userService.updateUser(user);
        return Result.success(i);
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<User> page = userService.findUsersByPage(pageNum, pageSize, search);
        return Result.success(page);
    }


    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        int i = userService.deleteUserById(id);
        return Result.success(i);
    }
}