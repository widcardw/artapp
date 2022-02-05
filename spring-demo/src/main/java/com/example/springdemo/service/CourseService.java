package com.example.springdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.entity.Course;

import java.util.List;

public interface CourseService {
   int insertCourse(Course course);
   int updateCourse(Course course);
   int deleteCourseById(Long id);
   Page<Course> selectCourseByPage(int pageNum, int pageSize, String search);
   boolean isCourseExist(String courseNo);
   List<String> selectAllCourseName();
   List<String> selectAllCourseNo();
   List<Course> selectAllCourse();
}
