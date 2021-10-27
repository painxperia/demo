package com.example.demo.test2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjun
 * @date 2021/7/19  14:08
 */
public class Test2 {
    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> list = new ArrayBlockingQueue<>(10);
        Test2 test2 = new Test2();
        new Thread(test2.new Consumer(list)).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(test2.new Producer(list)).start();
    }

    class Consumer implements Runnable {

        private ArrayBlockingQueue<Integer> list;

        public Consumer(ArrayBlockingQueue<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (list.isEmpty() || list.size() % 5 != 0) {
                    System.out.println("开始阻塞");
                    condition.await();
                }
//            TimeUnit.SECONDS.sleep(1);
                Integer take = list.take();
                System.out.println("take:" + take);
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
    }

    class Producer implements Runnable {
        private ArrayBlockingQueue<Integer> list;

        public Producer(ArrayBlockingQueue<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    list.add(i);
                    System.out.println("add " + (i + 1) + " element");
                    if (list.size() % 5 == 0) {
                        condition.signal();
                        System.out.println("生产者发送消息");
                    }

                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
    }
}


