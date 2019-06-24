package com.example.online.dao;

import com.example.online.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    /**
     * 用户登录
     * @param schoolNumber
     * @param schoolPassword
     * @return
     */
    User loginUser(@Param("schoolNumber") String schoolNumber, @Param("schoolPassword") String schoolPassword);

    /**
     * 注册用户
     * @param userName
     * @param schoolNumber
     * @param schoolPassword
     * @return
     */
    int insertUser(@Param("userName") String userName,@Param("schoolNumber") String schoolNumber, @Param("schoolPassword") String schoolPassword);

    /**
     * 注销用户
     * @param userId
     * @return
     */
    int deleteUser(long userId);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserInfo(User user);

}
