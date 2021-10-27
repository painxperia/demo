package com.example.demo.util.mapstruct.impl;

import com.example.demo.domain.UserInfo;
import com.example.demo.pojo.ZpUser;
import com.example.demo.util.mapstruct.UserConverter;

/**
 * @author zhangjun
 * @date 2021/10/22  10:47
 */
public abstract class UserConverterMapperImpl implements UserConverter {

    private final UserConverter converter;

    public UserConverterMapperImpl(UserConverter converter) {
        this.converter = converter;
    }

    @Override
    public UserInfo toUser(ZpUser zpUser) {
        UserInfo userInfo = converter.toUser(zpUser);
        userInfo.setEmail("111122");
        return userInfo;
    }

}
