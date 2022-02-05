package com.example.springdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.entity.Teacher;

public interface TeacherService {
    int insertTeacher(Teacher teacher);
    int deleteTeacherById(Long id);
    int updateTeacher(Teacher teacher);
    Teacher getTeacherByName(String name);
    Page<Teacher> getTeacherByPage(int pageNum, int pageSize, String search);
    boolean isExist(String name);
    Teacher login(Teacher teacher);
}
