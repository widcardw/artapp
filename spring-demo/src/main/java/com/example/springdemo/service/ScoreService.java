package com.example.springdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.entity.Score;
import com.example.springdemo.entity.ScoreVO;

public interface ScoreService {
    ScoreVO getScoreByStuIdAndCourseId(Integer stuId, Integer courseId);
    Page<ScoreVO> findPageByStuId(Integer stuId, Integer pageNum, Integer pageSize);
    Page<ScoreVO> findPageByStuName(String stuName, Integer pageNum, Integer pageSize);
    Page<ScoreVO> findPageByCourseId(Integer courseId, Integer pageNum, Integer pageSize);
    Page<ScoreVO> findPageByCourseNo(String courseNo, Integer pageNum, Integer pageSize);
    Page<ScoreVO> findPageByCourseName(String courseName, Integer pageNum, Integer pageSize);
    Page<ScoreVO> getAllScore(Integer pageNum, Integer pageSize);

    boolean insert(Score score);
    boolean update(Score score);
    boolean delete(Integer stuId, Integer courseId);
}
