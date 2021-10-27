package com.example.demo.test2;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Test3 {
    public static void main(String[] args) throws BrokenBarrierException,
            InterruptedException {
        // 定义屏障，这里parties值为11，目的是让主线程也可以等待
        CyclicBarrier barrier = new CyclicBarrier(11);
        for (int i = 0; i < 10; i++) {
            new Thread(new Tourist(i, barrier)).start();
        }
        // 主线程也进入阻塞，等待所有游客都上了车
        barrier.await();
        System.out.println("所有人都已经上车");
        // 主线程进入阻塞，等待所有游客都下车
        barrier.await();
        System.out.println("所有人都已经下车");
    }

    private static class Tourist implements Runnable {
        private final int id;
        private final CyclicBarrier barrier;

        public Tourist(int id, CyclicBarrier barrier) {
            this.id = id;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println("游客" + id + "上车");
            // 模拟游客上车时间开销
            System.out.println("游客" + id + "已经上车，等待其他人上车");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("游客" + id + "等待结束");
            // 模拟下车时间
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 下车后等待，等待其他同伴全部下车
            System.out.println("游客" + id + "已经下车，等待其他人下车");
            try {
            // 因为await会阻塞，所以当前面11个await阻塞没有结束之前，这里的await
                // 当前面11个线程全部等待结束后，再次await，如果CyclicBarrier计数器为0，会自动重置
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}