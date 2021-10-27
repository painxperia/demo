package com.example.demo.service;


import java.lang.reflect.Proxy;

/**
 * @author zhangjun
 * @date 2021/6/15  13:32
 */
public class Test {
    public static void main(String[] args) {
        HelloProxyInvocationHandler helloProxyInvocationHandler =
                new HelloProxyInvocationHandler(new HelloService());

        IHelloService iHelloService = (IHelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader()
                , new Class<?>[]{IHelloService.class}, helloProxyInvocationHandler);
        String s = iHelloService.sayHello("111");
        System.out.println("s:" + s);

    }
}
