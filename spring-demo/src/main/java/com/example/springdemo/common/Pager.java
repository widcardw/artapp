package com.example.springdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pager<T> {
    private List<T> content;
    private Long total;
    private Integer pageNum;
    private Integer pageSize;
}
