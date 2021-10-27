package com.example.demo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjun
 * @date 2021/9/29  15:14
 */
public class SynchronizedTest {
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test(1);
        test.test();
    }
}

class Test {

    private int NUM;

    public Test(int num) {
        this.NUM = num;
    }

    void test() throws InterruptedException {
        new Thread(() -> {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    while (NUM != 1) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("A");

                    NUM = 2;
                    this.notifyAll();
                }
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    while (NUM != 2) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print("B");

                    NUM = 3;
                    this.notifyAll();
                }
            }
        }, "B").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    while (NUM != 3) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("C");

                    NUM = 1;
                    this.notifyAll();
                }
            }
        }, "C").start();
    }
}
