package com.example.demo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zhangjun
 * @date 2021/7/22  13:59
 */
@Service
public class UserFactory implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setId(1L);
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

}
