package com.example.springdemo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Course;
import com.example.springdemo.mapper.CourseMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    CourseMapper courseMapper;

    @PostMapping  // 新增
    public Result<?> save(@RequestBody Course course) {
        // 判断课程号是否已存在
        if (existCourseNo(course.getCourseNo())) {
            return Result.error("-1", "课程号已经存在");
        }
        if (course.getCourseName() == null) {
            return Result.error("-2", "请输入课程名");
        }
        if (!NameValidater.usernameValid(course.getCourseNo())) {
            return Result.error("-3", "课程号不合法");
        }
        if (!NameValidater.nickNameValid(course.getCourseName())) {
            return Result.error("-4", "课程名不合法");
        }
        courseMapper.insert(course);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Course course) {
        if (course.getCourseName() == null) {
            return Result.error("-2", "请输入课程名");
        }
        if (!NameValidater.usernameValid(course.getCourseNo())) {
            return Result.error("-3", "课程号不合法");
        }
        if (!NameValidater.nickNameValid(course.getCourseName())) {
            return Result.error("-4", "课程名不合法");
        }
        courseMapper.updateById(course);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Course::getCourseName, search);
        }
        Page<Course> res = courseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(res);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        courseMapper.deleteById(id);
        return Result.success();
    }

    public boolean existCourseNo(String courseNo) {
        Course res = courseMapper.selectOne(Wrappers.<Course>lambdaQuery().eq(Course::getCourseNo, courseNo));
        return res != null;
    }
}
