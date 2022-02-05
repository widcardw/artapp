package com.example.springdemo.controller;


import cn.hutool.core.util.StrUtil;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Admin;
import com.example.springdemo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 注明为控制类 (@RestController)
 * api 路径 为 `/admin` (@RequestMapping)
 * 控制类基本上只实现传递过来的数据的预处理，并不涉及到数据库的 CRUD
 */
@RestController
@RequestMapping("/admin")  // 整个控制类下面的接口，都包含了 /admin
public class AdminController {

    /**
     * 通过 @Autowired 注解将 AdminService 注入到这个控制类中
     */
    @Autowired
    AdminService adminService;

    /**
     * 使用 post 请求，接口为 /admin/login，实现登录的控制
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin) {
        Admin res = adminService.login(admin);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误!");
        }
        return Result.success(res);
    }

    /**
     * 使用 get 请求，实现信息的获取
     */
    @GetMapping("/info")
    public Result<?> getAdminInfo(@RequestParam(defaultValue = "") String adminName) {
        if (StrUtil.isBlank(adminName)) {
            return Result.error("-2", "管理员信息获取失败!");
        }
        Admin res = adminService.getAdminInfoByAdminName(adminName);
        return Result.success(res);
    }

    /**
     * 使用 put 请求，实现管理员信息的修改
     * 其实感觉不是很恰当，因为这样的话直接在代码层就写死了
     * 应该可以在 Mapper 层进行一些优化，例如传入某一个为空的时候不对那一部分的数据进行修改
     * 具体的在 MyBatis 的 <if></if> 标签中实现
     */
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
        int i = adminService.updateAdminInfo(admin);
        return Result.success(i);
    }
}
