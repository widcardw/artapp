package com.example.springdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("administrator")
@Data
public class Admin {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String adminName;
    private String nickName;
    private String password;
}
