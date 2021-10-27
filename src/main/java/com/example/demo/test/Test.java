package com.example.demo.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjun
 * @date 2021/7/16  14:04
 */
public class Test {
    public static void main(String[] args) {
        Object o = new Object();
        ArrayBlockingQueue<Integer> list = new ArrayBlockingQueue<>(10);
        new Thread(new com.example.demo.test.Consumer(list, o)).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Producer(list, o)).start();
    }

}

class Consumer implements Runnable {
    private ArrayBlockingQueue<Integer> list;
    private Object object;

    public Consumer(ArrayBlockingQueue<Integer> list, Object object) {
        this.list = list;
        this.object = object;
    }

    @Override
    public void run() {
        synchronized (object) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (list.size() % 5 == 0) {
                    System.out.println("消费者开始等待");
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Integer take = list.take();
                    System.out.println(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                object.notify();
//                System.out.println("消费者结束");
            }
        }
    }
}

class Producer implements Runnable {
    private ArrayBlockingQueue<Integer> list;
    private Object object;

    public Producer(ArrayBlockingQueue<Integer> list, Object object) {
        this.list = list;
        this.object = object;
    }

    @Override
    public void run() {
        synchronized (object) {
            System.out.println("生产者开始生产");
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("add " + (i + 1) + " element");
                if (list.size() % 5 == 0) {
                    object.notify();
                    System.out.println("生产者发送消息");
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}