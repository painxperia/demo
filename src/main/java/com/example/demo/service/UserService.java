package com.example.demo.service;

import com.example.demo.User;

import java.util.List;

/**
 * @author zhangjun
 * @date 2021/6/21  17:00
 */
public interface UserService<T> {

    Boolean insertUser(User user);

    Boolean updateUser(User user);

    List<T> search(User user,Class<T> clazz);
}
