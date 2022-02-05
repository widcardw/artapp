package com.example.springdemo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Course;
import com.example.springdemo.mapper.CourseMapper;
import com.example.springdemo.service.CourseService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping  // 新增
    public Result<?> save(@RequestBody Course course) {
        // 判断课程号是否已存在
        if (courseService.isCourseExist(course.getCourseNo())) {
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
        int i = courseService.insertCourse(course);
        return Result.success(i);
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
        int i = courseService.updateCourse(course);
        return Result.success(i);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<Course> res = courseService.selectCourseByPage(pageNum, pageSize, search);
        return Result.success(res);
    }

    @GetMapping("/no")
    public Result<?> getAllCourseNo() {
        List<String> data = courseService.selectAllCourseNo();
        return Result.success(data);
    }

    @GetMapping("/name")
    public Result<?> getAllCourseName() {
        List<String> data = courseService.selectAllCourseName();
        return Result.success(data);
    }

    @GetMapping("/all")
    public Result<?> getAllCourse() {
        List<Course> data = courseService.selectAllCourse();
        return Result.success(data);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        int i = courseService.deleteCourseById(id);
        return Result.success(i);
    }

}
