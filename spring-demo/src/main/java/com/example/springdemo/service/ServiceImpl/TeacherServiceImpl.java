package com.example.springdemo.service.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.entity.Teacher;
import com.example.springdemo.mapper.TeacherMapper;
import com.example.springdemo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TeacherService")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public int insertTeacher(Teacher teacher) {
        return teacherMapper.insert(teacher);
    }

    @Override
    public int deleteTeacherById(Long id) {
        return teacherMapper.deleteById(id);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateById(teacher);
    }

    @Override
    public Teacher getTeacherByName(String name) {
        return teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTeacherName, name));
    }

    @Override
    public Page<Teacher> getTeacherByPage(int pageNum, int pageSize, String search) {
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.<Teacher>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Teacher::getTeacherName, search);
        }
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        return teacherMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean isExist(String name) {
        return teacherMapper.selectCount(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTeacherName, name)) > 0;
    }

    @Override
    public Teacher login(Teacher teacher) {
        return teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getTeacherName, teacher.getTeacherName())
                .eq(Teacher::getPassword, teacher.getPassword()));
    }
}
