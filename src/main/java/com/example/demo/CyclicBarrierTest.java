package com.example.demo;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjun
 * @date 2021/8/20  15:13
 */
public class CyclicBarrierTest {

    @SuppressWarnings("AlibabaThreadPoolCreation")
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(5,()->{
            System.out.println("----1111----");
        });
        @SuppressWarnings("AlibabaThreadPoolCreation") ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            final int num = i;
            TimeUnit.SECONDS.sleep(1);
            executorService.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+",start:" + num);
                    barrier.await();
                    System.out.println(Thread.currentThread().getName()+",end:" + num);
                } catch (Exception e) {

                }

            });
        }
        executorService.shutdown();
    }

}
