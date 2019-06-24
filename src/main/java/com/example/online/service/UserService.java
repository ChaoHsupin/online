package com.example.online.service;

import com.example.online.entity.User;

public interface UserService {
    User loginUser(String schoolNumber,  String schoolPassword);
    int insertUser(String userName,String schoolNumber,  String schoolPassword);
    int deleteUser(long userId);
    int updateUserInfo(User user);
}
