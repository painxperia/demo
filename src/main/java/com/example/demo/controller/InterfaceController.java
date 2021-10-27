package com.example.demo.controller;

import com.example.demo.domain.Params;
import com.example.demo.domain.ParamsInfo;
import com.example.demo.service.redis.RedisManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangjun
 * @date 2021/9/16  9:37
 */
@RestController
public class InterfaceController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/aaa")
    public void test(@RequestBody ParamsInfo paramsInfo) throws UnirestException {
        String key = paramsInfo.getKey();
        Optional<Object> o = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        if (o.isPresent()){
            String url = o.toString();
            HttpResponse<String> stringHttpResponse =
                    Unirest.post(url).body(paramsInfo.getRequestParams()).asString();
        }
    }

    public static void main(String[] args) {
        int sum = sum();
        System.out.println(sum);
    }

    public static int sum(){
        int a = 100;
        int b = 200;
        int c = 300;
        return (a+b)*c;
    }
}
