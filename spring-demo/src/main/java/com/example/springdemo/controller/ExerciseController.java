package com.example.springdemo.controller;

import com.example.springdemo.common.Pager;
import com.example.springdemo.common.Result;
import com.example.springdemo.entity.Exercise;
import com.example.springdemo.service.ExerciseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Resource
    ExerciseService exerciseService;

    @GetMapping("/page")
    public Result<?> findByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Pager<Exercise> page = exerciseService.getExerciseByPage(pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/id/{id}")
    public Result<?> findById(@PathVariable("id") Integer id) {
        Exercise ex = exerciseService.getExerciseById(id);
        return Result.success(ex);
    }

    @GetMapping("/type/{type}")
    public Result<?> findByType(@PathVariable("type") String type,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Pager<Exercise> page = exerciseService.getExerciseByType(type, pageNum, pageSize);
        return Result.success(page);
    }

    @PostMapping("/one")
    public Result<?> insertOne(@RequestBody Exercise exercise) {
        Exercise exercise1 = exerciseService.insertOne(exercise);
        System.out.println(exercise);
        return Result.success(exercise1);
    }

    @DeleteMapping("/id/{id}")
    public Result<?> deleteById(@PathVariable("id") String id) {
        exerciseService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/one")
    public Result<?> updateOne(@RequestBody Exercise exercise) {
        exerciseService.updateExercise(exercise);
        return Result.success();
    }
}
