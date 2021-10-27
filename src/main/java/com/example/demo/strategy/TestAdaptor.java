package com.example.demo.strategy;

import com.example.demo.strategy.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangjun
 * @date 2021/6/2  10:28
 */
@Component
public class TestAdaptor {

    @Autowired
    private List<TestService> list;

    private static Map<Integer, TestService> map = new HashMap<>(16);

    @PostConstruct
    public void init() {
        if (list.size() > 0) {
            map = list.stream().collect(Collectors.toMap(TestService::getCode, Function.identity()));
        }
    }
//    public TestAdaptor(List<TestService> list) {
//        map = list.stream().collect(Collectors.toMap(TestService::getCode, Function.identity()));
//    }

    public TestAdaptor(Map<Integer,TestService> map2) {
        map.putAll(map2);
    }

    public TestService getTestService(Integer code){
        return map.get(code);
    }
}
