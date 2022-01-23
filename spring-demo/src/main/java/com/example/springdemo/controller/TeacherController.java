package com.example.springdemo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Admin;
import com.example.springdemo.entity.Teacher;
import com.example.springdemo.mapper.TeacherMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    TeacherMapper teacherMapper;

    @PostMapping
    public Result<?> save(@RequestBody Teacher teacher) {
        if (existsUserName(teacher.getTeacherName())) {
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
        teacherMapper.insert(teacher);
        return Result.success();
    }

    @PostMapping("/login")  // 接口为 /teacher/login
    public Result<?> login(@RequestBody Teacher teacher) {
        Teacher res = teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getTeacherName, teacher.getTeacherName())
                .eq(Teacher::getPassword, teacher.getPassword()));
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
        teacherMapper.updateById(teacher);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<?> getTeacherInfo(@RequestParam(defaultValue = "") String teacherName) {
        if (StrUtil.isBlank(teacherName)) {
            return Result.error("-2", "教师信息获取失败!");
        }
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.<Teacher>lambdaQuery();
        wrapper.eq(Teacher::getTeacherName, teacherName);
        Teacher res = teacherMapper.selectOne(wrapper);
        return Result.success(res);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.<Teacher>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Teacher::getTeacherName, search);
        }
        Page<Teacher> teacherPage = teacherMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(teacherPage);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        teacherMapper.deleteById(id);
        return Result.success();
    }

    private boolean existsUserName(String username) {
        Teacher teacher = teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTeacherName, username));
        return teacher != null;
    }
}
