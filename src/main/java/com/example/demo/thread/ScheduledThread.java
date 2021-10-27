package com.example.demo.thread;

import java.util.concurrent.*;

/**
 * @author zhangjun
 * @date 2021/9/28  15:28
 */
public class ScheduledThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 5; i++) {
            ScheduledFuture<Integer> schedule = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int j = 0; j < 5; j++) {
                        sum += j;
                    }
                    return sum;
                }
            }, 2, TimeUnit.SECONDS);
            System.out.println(schedule.get());
        }
        pool.shutdown();
    }
}
