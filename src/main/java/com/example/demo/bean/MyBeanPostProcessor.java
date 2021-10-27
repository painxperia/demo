package com.example.demo.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhangjun
 * @date 2021/7/22  15:09
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        System.out.println("BeanPostProcessor实现类构造函数...");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("lagouBean".equals(beanName)){
            System.out.println("BeanPostProcessor 实现类postProcessBeforeInitialization ⽅法被调⽤中......");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("lagouBean".equals(beanName)){
            System.out.println("BeanPostProcessor 实现类postProcessAfterInitialization ⽅法被调⽤中......");
        }
        return bean;
    }
}
