package com.example.springdemo.service.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.entity.Course;
import com.example.springdemo.mapper.CourseMapper;
import com.example.springdemo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CourseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public int insertCourse(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateById(course);
    }

    @Override
    public int deleteCourseById(Long id) {
        return courseMapper.deleteById(id);
    }

    @Override
    public Page<Course> selectCourseByPage(int pageNum, int pageSize, String search) {
        LambdaQueryWrapper<Course> wrapper = Wrappers.lambdaQuery(Course.class);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Course::getCourseName, search);
        }
        return courseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public boolean isCourseExist(String courseNo) {
        return courseMapper.selectCount(Wrappers.lambdaQuery(Course.class).eq(Course::getCourseNo, courseNo)) > 0;
    }

    @Override
    public List<String> selectAllCourseName() {
        return courseMapper.selectAllCourseName();
    }

    @Override
    public List<String> selectAllCourseNo() {
        return courseMapper.selectAllCourseNo();
    }

    @Override
    public List<Course> selectAllCourse() {
        return courseMapper.selectAllCourse();
    }
}
