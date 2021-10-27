package com.example.demo.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjun
 * @date 2021/10/8  10:43
 */
public class LockTest2 {

    public static void main(String[] args) {
        TestLock testLock = new TestLock(1);
        testLock.test();
    }
}

class TestLock {
    private int num;

    public TestLock(int num) {
        this.num = num;
    }

    public void test() {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    while (num!=1){
                        condition.await();
                    }
                    System.out.print("A");
                    num = 2;
                    condition.signalAll();
                }
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    while (num!=2){
                        condition.await();
                    }
                    System.out.print("B");
                    num = 3;
                    condition.signalAll();
                }
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        }, "B").start();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    while (num!=3){
                        condition.await();
                    }
                    System.out.println("C");
                    num = 1;
                    condition.signalAll();
                }
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        }, "C").start();
    }

}
