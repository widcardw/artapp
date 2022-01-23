package com.example.springdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("teacher")
@Data
public class Teacher {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String teacherName;
    private String password;
    private String nickName;
}
