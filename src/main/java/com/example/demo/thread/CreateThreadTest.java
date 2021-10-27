package com.example.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author zhangjun
 * @date 2021/9/28  13:55
 */
public class CreateThreadTest {

    public static void main(String[] args) throws Exception {
//        new Thread(new RunnableDemo()).start();
//        new ThreadDemo().start();
        FutureTask<Integer> task = new FutureTask<>(new CallAbleDemo());
        new Thread(task).start();
        System.out.println(task.get());
    }
}

class RunnableDemo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}

class ThreadDemo extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}

class CallAbleDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 10; i++) {
            sum += i;
        }
        return sum;
    }
}