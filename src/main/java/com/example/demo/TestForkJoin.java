package com.example.demo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author zhangjun
 * @date 2021/6/21  10:24
 */
public class TestForkJoin extends RecursiveTask<Integer> {
    private static final Integer MAX = 50;

    private Integer startValue;
    private Integer endValue;

    public TestForkJoin(Integer startValue, Integer endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    protected Integer compute() {
        if (endValue - startValue < MAX) {
            System.out.println(Thread.currentThread().getName() + "开始计算的部分：startValue = " + startValue + ";endValue = " + endValue);
            return IntStream.rangeClosed(this.startValue, this.endValue).sum();
        } else {
            TestForkJoin forkJoin1 = new TestForkJoin(startValue, (startValue + endValue) / 2);
            forkJoin1.fork();
            TestForkJoin forkJoin2 = new TestForkJoin((startValue + endValue) / 2 + 1, endValue);
            forkJoin2.fork();
            return forkJoin1.join() + forkJoin2.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(new TestForkJoin(1, 200));
        try {
            Integer integer = submit.get();
            System.out.println("sum:" + integer);
        } catch (Exception e) {
            System.out.println("e:" + e);
        }
    }
}
