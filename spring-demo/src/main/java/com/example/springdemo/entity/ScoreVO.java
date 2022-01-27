package com.example.springdemo.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScoreVO {
    private Integer stuId;
    private String stuName;
    private String stuNickName;
    private String stuPassword;

    private Integer courseId;
    private String courseNo;
    private String courseName;

    private Integer score;
}
