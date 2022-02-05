package com.example.springdemo.service;

import com.example.springdemo.common.Pager;
import com.example.springdemo.entity.Exercise;

public interface ExerciseService {
    Pager<Exercise> getExerciseByPage(Integer pageNum, Integer pageSize);
    Exercise getExerciseById(Integer id);
    Pager<Exercise> getExerciseByType(String type,Integer pageNum, Integer pageSize);
    Exercise insertOne(Exercise exercise);
    void deleteById(String _id);
    void updateExercise(Exercise exercise);
}
