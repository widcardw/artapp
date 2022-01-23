package com.example.springdemo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Admin;
import com.example.springdemo.mapper.AdminMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminMapper adminMapper;

    @PostMapping("/login")  // 接口为 /admin/login
    public Result<?> login(@RequestBody Admin admin) {
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getAdminName, admin.getAdminName())
                .eq(Admin::getPassword, admin.getPassword()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误!");
        }
        return Result.success(res);
    }

    @GetMapping("/info")
    public Result<?> getAdminInfo(@RequestParam(defaultValue = "") String adminName) {
        if (StrUtil.isBlank(adminName)) {
            return Result.error("-2", "管理员信息获取失败!");
        }
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery();
        wrapper.eq(Admin::getAdminName, adminName);
        Admin res = adminMapper.selectOne(wrapper);
        return Result.success(res);
    }

    @PutMapping
    public Result<?> update(@RequestBody Admin admin) {
        // 判断发送过来的密码是否为空
        if (admin.getPassword() == null) {
            admin.setPassword("123456");
        }
        // 判断发送过来的昵称是否为空
        if (admin.getNickName() == null) {
            admin.setNickName("Default");
        }
        // 判断用户名是否合法
        if (!NameValidater.usernameValid(admin.getAdminName())) {
            return Result.usernameError();
        }
        // 判断昵称是否合法
        if (!NameValidater.nickNameValid(admin.getNickName())) {
            return Result.nickNameError();
        }
        adminMapper.updateById(admin);
        return Result.success();
    }

    public boolean existsUserName(String adminName) {
        Admin admin = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminName, adminName));
        return admin != null;
    }
}
