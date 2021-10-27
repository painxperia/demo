package com.example.demo.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhangjun
 * @date 2021/6/15  13:28
 */
public class HelloProxyInvocationHandler implements InvocationHandler {

    private Object obj;

    public HelloProxyInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object invoke = method.invoke(obj, args);
        System.out.println("after");
        return invoke;
    }
}
