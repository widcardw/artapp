package com.example.springdemo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.User;
import com.example.springdemo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    /**
     * 新增
     * @return Result
     */
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        if (existsUserName(user.getUsername())) {
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
        userMapper.insert(user);
        return Result.success();
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
        userMapper.updateById(user);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUsername, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return Result.success();
    }

    private boolean existsUserName(String username) {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        return user != null;
    }
}
