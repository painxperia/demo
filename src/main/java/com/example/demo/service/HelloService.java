package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * @author zhangjun
 * @date 2021/6/15  13:24
 */
@Service
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String message) {
        System.out.println("message:" + message);
        return "message:" + message;
    }
}
