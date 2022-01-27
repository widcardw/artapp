package com.example.springdemo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Score;
import com.example.springdemo.entity.ScoreVO;
import com.example.springdemo.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    ScoreService scoreService;

    @GetMapping("/stuId/{stuId}")
    public Result<?> getScoreByStuId(@PathVariable Integer stuId,
                                     @RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize) {
        Page<ScoreVO> pager = scoreService.findPageByStuId(stuId, pageNum, pageSize);
        return Result.success(pager);
    }

    @GetMapping("/courseId/{courseId}")
    public Result<?> getScoreByCourseId(@PathVariable Integer courseId,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ScoreVO> pager = scoreService.findPageByCourseId(courseId, pageNum, pageSize);
        return Result.success(pager);
    }

    @GetMapping("/stuName/{stuName}")
    public Result<?> getScoreByStuName(@PathVariable String stuName,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ScoreVO> pager = scoreService.findPageByStuName(stuName, pageNum, pageSize);
        return Result.success(pager);
    }

    @GetMapping("/courseName/{courseName}")
    public Result<?> getScoreByCourseName(@PathVariable String courseName,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ScoreVO> pager = scoreService.findPageByCourseName(courseName, pageNum, pageSize);
        return Result.success(pager);
    }

    @GetMapping("/courseNo/{courseNo}")
    public Result<?> getScoreByCourseNo(@PathVariable String courseNo,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ScoreVO> page = scoreService.findPageByCourseNo(courseNo, pageNum, pageSize);
        return Result.success(page);
    }

    @PutMapping
    public Result<?> update(@RequestBody Score score) {
        boolean b = scoreService.update(score);
        if (!b) {
            return Result.error("-2", "成绩更新失败");
        }
        return Result.success();
    }
}
