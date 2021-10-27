package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangjun
 * @date 2021/10/21  13:13
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
