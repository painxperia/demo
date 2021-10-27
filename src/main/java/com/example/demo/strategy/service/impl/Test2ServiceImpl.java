package com.example.demo.strategy.service.impl;

import com.example.demo.strategy.service.TestService;
import org.springframework.stereotype.Component;

/**
 * @author zhangjun
 * @date 2021/6/2  10:29
 */
@Component
public class Test2ServiceImpl implements TestService {
    @Override
    public Integer getCode() {
        return 2;
    }

    @Override
    public void test() {
        System.out.println(2);
    }
}
