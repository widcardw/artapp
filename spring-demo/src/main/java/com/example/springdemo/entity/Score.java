package com.example.springdemo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sc")
@Data
public class Score {
    @TableId(value = "stu_id")
    private Integer stuId;
    @TableId(value = "course_id")
    private Integer courseId;
    private Integer score;
}
