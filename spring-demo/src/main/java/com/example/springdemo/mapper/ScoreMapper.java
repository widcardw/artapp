package com.example.springdemo.mapper;

import com.example.springdemo.entity.Score;
import com.example.springdemo.entity.ScoreVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreMapper {
    ScoreVO getScoreByStuIdAndCourseId(Map<String, Object> params);
    List<ScoreVO> findPageByStuId(Map<String, Object> params);
    List<ScoreVO> findPageByStuName(Map<String, Object> params);
    List<ScoreVO> findPageByCourseId(Map<String, Object> params);
    List<ScoreVO> findPageByCourseNo(Map<String, Object> params);
    List<ScoreVO> findPageByCourseName(Map<String, Object> params);
    List<ScoreVO> getAllScore(Map<String, Object> params);
    Long countAll();
    Long countByStuId(Integer stuId);
    Long countByStuName(String stuName);
    Long countByCourseId(Integer courseId);
    Long countByCourseNo(String courseNo);
    Long countByCourseName(String courseName);

    boolean insert(Score score);
    boolean update(Score score);
    boolean delete(Map<String, Object> params);
}
