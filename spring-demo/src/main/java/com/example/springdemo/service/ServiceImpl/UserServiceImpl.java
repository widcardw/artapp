package com.example.springdemo.service.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springdemo.entity.User;
import com.example.springdemo.mapper.UserMapper;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int deleteUserById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Page<User> findUsersByPage(int pageNum, int pageSize, String search) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            queryWrapper.like(User::getUsername, search);
        }
        return userMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public boolean existUserName(String userName) {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userName));
        return user != null;
    }
}
