package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjun
 * @date 2021/8/3  9:36
 */
@Getter
@Setter
@Slf4j
public class UserInfo {
    private Long id;

    private String name;

    private Integer age;

    public static void main(String[] args) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 3
                , TimeUnit.SECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i <= 100; i++) {
            final int a = i;
            executor.submit(()->{
                System.out.println(Thread.currentThread().getName() + ":" + a);
                try {
                    //TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    log.error("eee:",e.getMessage());
                }finally {
                    //log.info("queue:{}",queue.size());
                }
            });
        }
    }
}
