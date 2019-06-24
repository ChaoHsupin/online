package com.example.online.service.impl;

import com.example.online.dao.UserDao;
import com.example.online.entity.User;
import com.example.online.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User loginUser(String schoolNumber, String schoolPassword) {
        User user= userDao.loginUser(schoolNumber,schoolPassword);
        if(user==null){
            return null;
        }
        else{
            return user;
        }
    }

    @Override
    public int insertUser(String userName, String schoolNumber, String schoolPassword) {
        return userDao.insertUser(userName,schoolNumber,schoolPassword);
    }

    @Override
    public int deleteUser(long userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }
}
