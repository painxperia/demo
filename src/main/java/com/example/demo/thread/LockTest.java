package com.example.demo.thread;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjun
 * @date 2021/9/29  16:25
 */
public class LockTest {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+" start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+" end");
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        },"A").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+" start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+" end");
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        },"B").start();

    }

}
