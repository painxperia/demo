package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.ZpUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhangjun
 * @date 2021/10/20  15:35
 */
@Repository
public interface UserMapper extends BaseMapper<ZpUser> {
}
