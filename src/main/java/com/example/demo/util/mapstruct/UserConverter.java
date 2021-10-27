package com.example.demo.util.mapstruct;

import com.example.demo.domain.UserInfo;
import com.example.demo.pojo.ZpUser;
import com.example.demo.util.mapstruct.impl.UserConverterMapperImpl;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author zhangjun
 * @date 2021/10/20  16:00
 */
@Mapper
@DecoratedWith(UserConverterMapperImpl.class)
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * 用户转换
     *
     * @param zpUser
     * @return
     */
    @Mapping(target = "username",source = "username")
//    @BeanMapping(ignoreByDefault = true)
    UserInfo toUser(ZpUser zpUser);

    /**
     * 用户转换
     *
     * @param userInfo
     * @return
     */
    ZpUser toZpUser(UserInfo userInfo);

}
