package com.example.springdemo.service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springdemo.entity.Admin;
import com.example.springdemo.mapper.AdminMapper;
import com.example.springdemo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务层接口的实现
 * 其实这里的数据库查询应该要在 Mapper 层进行实现，Service 层仅仅是为了提供一个接口
 * 由于用了 MyBatis Plus，查询语句已经在 LambdaQueryWrapper 中实现了，所以就在 Service 层实现了查询
 */
@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin getAdminInfoByAdminName(String adminName) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery();
        wrapper.eq(Admin::getAdminName, adminName);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public int updateAdminInfo(Admin admin) {
        return adminMapper.updateById(admin);
    }

    @Override
    public Admin login(Admin admin) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getAdminName, admin.getAdminName())
                .eq(Admin::getPassword, admin.getPassword());
        return adminMapper.selectOne(wrapper);
    }
}
