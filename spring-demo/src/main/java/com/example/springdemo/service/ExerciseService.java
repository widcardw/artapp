package com.example.springdemo.service;

import com.example.springdemo.common.Pager;
import com.example.springdemo.entity.Exercise;
import org.springframework.data.domain.Page;

public interface ExerciseService {
    public Pager<Exercise> getExerciseByPage(Integer pageNum, Integer pageSize);
    public Exercise getExerciseById(Integer id);
    public Pager<Exercise> getExerciseByType(String type,Integer pageNum, Integer pageSize);
}
