package com.example.springdemo.service;

import com.example.springdemo.entity.Admin;

/**
 * 业务层接口
 */
public interface AdminService {
    Admin getAdminInfoByAdminName(String adminName);
    int updateAdminInfo(Admin admin);
    Admin login(Admin admin);
}
