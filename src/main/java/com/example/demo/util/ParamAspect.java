package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.WebLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhangjun
 * @date 2021/6/15  15:24
 */
@Component
@Aspect
public class ParamAspect {

//    @Pointcut("execution( * com.example.demo.*.*(*))")
//    public void doOperation() {
//
//    }

    @Pointcut("@annotation(com.example.demo.WebLog)")
    public void doOperation() {

    }

//    @Before("doOperation()")
//    public void before(JoinPoint joinPoint) throws Exception {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        boolean b = method.isAnnotationPresent(WebLog.class);
//        if (b) {
//            WebLog annotation = method.getAnnotation(WebLog.class);
//            String value = annotation.value();
//            String data = annotation.data();
//            System.out.println("value:" + value);
//            System.out.println("data:" + data);
//            Object[] args = joinPoint.getArgs();
//            for (Object obj : args) {
//                String s = JSON.toJSONString(obj);
//                System.out.println("params:" + s);
//            }
//            return;
//        }
//        throw new RuntimeException("no data");
//    }

    @Around("doOperation()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable, Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        boolean b = method.isAnnotationPresent(WebLog.class);
        if (b) {
            WebLog annotation = method.getAnnotation(WebLog.class);
            String value = annotation.value();
            String data = annotation.data();
            System.out.println("value:" + value);
            System.out.println("data:" + data);
            Object[] args = joinPoint.getArgs();
            for (Object obj : args) {
                String s = JSON.toJSONString(obj);
                System.out.println("params:" + s);
            }
            System.out.println("result:" + JSON.toJSONString(joinPoint.proceed()));
            return joinPoint.proceed();
        }
        throw new RuntimeException("no data");
    }

//    @AfterReturning(returning = "obj", pointcut = "doOperation()")
//    public void doAfterReturning(Object obj) {
//        System.out.println("result:" + JSON.toJSONString(obj));
//    }
//
//    @After("doOperation()")
//    public void after(JoinPoint joinPoint) {
//
//    }


}
