package com.example.demo.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author zhangjun
 * @date 2021/7/22  15:07
 */
@Component
public class LagouBean implements InitializingBean {
    public LagouBean() {
        System.out.println("LagouBean 构造器。。。");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LagouBean afterPropertiesSet...");
    }
}
