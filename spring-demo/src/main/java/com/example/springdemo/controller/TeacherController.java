package com.example.springdemo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Admin;
import com.example.springdemo.entity.Teacher;
import com.example.springdemo.mapper.TeacherMapper;
import com.example.springdemo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping
    public Result<?> save(@RequestBody Teacher teacher) {
        if(teacherService.isExist(teacher.getTeacherName())) {
            return Result.error("-1", "教师名重复");
        }
        // 判断发送过来的密码是否为空
        if (teacher.getPassword() == null) {
            teacher.setPassword("123456");
        }
        // 判断发送过来的昵称是否为空
        if (teacher.getNickName() == null) {
            teacher.setNickName("Default");
        }
        // 判断用户名是否合法
        if (!NameValidater.usernameValid(teacher.getTeacherName())) {
            return Result.usernameError();
        }
        // 判断昵称是否合法
        if (!NameValidater.nickNameValid(teacher.getNickName())) {
            return Result.nickNameError();
        }
        int i = teacherService.insertTeacher(teacher);
        return Result.success(i);
    }

    @PostMapping("/login")  // 接口为 /teacher/login
    public Result<?> login(@RequestBody Teacher teacher) {
        Teacher res = teacherService.login(teacher);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误!");
        }
        return Result.success(res);
    }

    @PutMapping
    public Result<?> update(@RequestBody Teacher teacher) {
        // 判断发送过来的密码是否为空
        if (teacher.getPassword() == null) {
            teacher.setPassword("123456");
        }
        // 判断发送过来的昵称是否为空
        if (teacher.getNickName() == null) {
            teacher.setNickName("Default");
        }
        // 判断用户名是否合法
        if (!NameValidater.usernameValid(teacher.getTeacherName())) {
            return Result.usernameError();
        }
        // 判断昵称是否合法
        if (!NameValidater.nickNameValid(teacher.getNickName())) {
            return Result.nickNameError();
        }
        int i = teacherService.updateTeacher(teacher);
        return Result.success(i);
    }

    @GetMapping("/info")
    public Result<?> getTeacherInfo(@RequestParam(defaultValue = "") String teacherName) {
        if (StrUtil.isBlank(teacherName)) {
            return Result.error("-2", "教师信息获取失败!");
        }
        Teacher res = teacherService.getTeacherByName(teacherName);
        return Result.success(res);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<Teacher> teacherByPage = teacherService.getTeacherByPage(pageNum, pageSize, search);
        return Result.success(teacherByPage);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return Result.success();
    }
}