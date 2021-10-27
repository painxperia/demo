package com.example.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangjun
 * @date 2021/9/28  15:21
 */
public class TestThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            service.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + index);
            });
        }
        service.shutdown();
    }
}
