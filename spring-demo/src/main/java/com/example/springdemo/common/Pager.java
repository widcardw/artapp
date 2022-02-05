package com.example.springdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自定义类 Pager 用于查找题目的返回
 * @param <T> T 表示一个泛型
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pager<T> {
    private List<T> content;
    private Long total;
    private Integer pageNum;
    private Integer pageSize;
}
