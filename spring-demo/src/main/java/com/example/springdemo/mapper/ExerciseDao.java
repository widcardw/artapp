package com.example.springdemo.mapper;

import com.example.springdemo.entity.Exercise;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseDao extends PagingAndSortingRepository<Exercise, String> {}
