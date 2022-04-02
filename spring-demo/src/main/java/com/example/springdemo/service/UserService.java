package com.example.springdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springdemo.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UserService extends IService<User> {
    int insertUser(User user);
    int updateUser(User user);
    int deleteUserById(Long id);
    Page<User> findUsersByPage(int page, int size, String search);
    boolean existUserName(String userName);
}
