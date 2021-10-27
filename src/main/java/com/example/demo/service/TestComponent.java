package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangjun
 * @date 2021/9/16  10:08
 */
@Component
@Slf4j
public class TestComponent {

    @Autowired
    private IHelloService helloService;

    public String test() {
        String a = helloService.sayHello("aaaaaa");
        log.info("aaaa:{}", a);
        return a;
    }
}
