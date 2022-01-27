package com.example.springdemo.service.ServiceImpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.entity.Score;
import com.example.springdemo.entity.ScoreVO;
import com.example.springdemo.mapper.ScoreMapper;
import com.example.springdemo.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public ScoreVO getScoreByStuIdAndCourseId(Integer stuId, Integer courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("stuId", stuId);
        params.put("courseId", courseId);
        return scoreMapper.getScoreByStuIdAndCourseId(params);
    }

    @Override
    public Page<ScoreVO> findPageByStuId(Integer stuId, Integer pageNum, Integer pageSize) {
        // 通过 Map 传参
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("stuId", stuId);
        // 获取查询的页面
        Page<ScoreVO> pager = new Page<>();
        List<ScoreVO> scoreList = scoreMapper.findPageByStuId(params);
        // 将查询的结果放入 pager 中
        pager.setPages(pageNum);
        pager.setSize(pageSize);
        pager.setTotal(scoreMapper.countByStuId(stuId));
        pager.setRecords(scoreList);
        return pager;
    }

    @Override
    public Page<ScoreVO> findPageByStuName(String stuName, Integer pageNum, Integer pageSize) {
        // 通过 Map 传参
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("stuName", stuName);
        // 获取查询的页面
        Page<ScoreVO> pager = new Page<>();
        List<ScoreVO> scoreList = scoreMapper.findPageByStuName(params);
        // 将查询的结果放入 pager 中
        pager.setPages(pageNum);
        pager.setSize(pageSize);
        pager.setTotal(scoreMapper.countByStuName(stuName));
        pager.setRecords(scoreList);
        return pager;
    }

    @Override
    public Page<ScoreVO> findPageByCourseId(Integer courseId, Integer pageNum, Integer pageSize) {
        // 通过 Map 传参
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("courseId", courseId);
        // 获取查询的页面
        Page<ScoreVO> pager = new Page<>();
        List<ScoreVO> scoreList = scoreMapper.findPageByCourseId(params);
        // 将查询的结果放入 pager 中
        pager.setPages(pageNum);
        pager.setSize(pageSize);
        pager.setTotal(scoreMapper.countByCourseId(courseId));
        pager.setRecords(scoreList);
        return pager;
    }

    @Override
    public Page<ScoreVO> findPageByCourseNo(String courseNo, Integer pageNum, Integer pageSize) {
        // 通过 Map 传参
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("courseNo", courseNo);
        // 获取查询的页面
        Page<ScoreVO> pager = new Page<>();
        List<ScoreVO> scoreList = scoreMapper.findPageByCourseNo(params);
        // 将查询的结果放入 pager 中
        pager.setPages(pageNum);
        pager.setSize(pageSize);
        pager.setTotal(scoreMapper.countByCourseNo(courseNo));
        pager.setRecords(scoreList);
        return pager;
    }

    @Override
    public Page<ScoreVO> findPageByCourseName(String courseName, Integer pageNum, Integer pageSize) {
        // 通过 Map 传参
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("courseName", "%" + courseName + "%");
        // 获取查询的页面
        Page<ScoreVO> pager = new Page<>();
        List<ScoreVO> scoreList = scoreMapper.findPageByCourseName(params);
        // 将查询的结果放入 pager 中
        pager.setPages(pageNum);
        pager.setSize(pageSize);
        pager.setTotal(scoreMapper.countByCourseName(courseName));
        pager.setRecords(scoreList);
        return pager;
    }

    @Override
    public Page<ScoreVO> getAllScore(Integer pageNum, Integer pageSize) {
        // 通过 Map 传参
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        // 获取查询页面
        Page<ScoreVO> pager = new Page<>();
        List<ScoreVO> scoreList = scoreMapper.getAllScore(params);
        // 将查询的结果放入 pager 中
        pager.setPages(pageNum);
        pager.setSize(pageSize);
        pager.setTotal(scoreMapper.countAll());
        pager.setRecords(scoreList);
        return pager;
    }

    @Override
    public boolean insert(Score score) {
        return scoreMapper.insert(score);
    }

    @Override
    public boolean update(Score score) {
        return scoreMapper.update(score);
    }

    @Override
    public boolean delete(Integer stuId, Integer courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("stuId", stuId);
        params.put("courseId", courseId);
        return scoreMapper.delete(params);
    }
}
