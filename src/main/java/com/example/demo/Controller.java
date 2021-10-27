package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.TestComponent;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangjun
 * @date 2021/6/15  15:45
 */
@RestController
@Slf4j
//@PreAuthorize("hasRole('dev')")
public class Controller {
    @Autowired
    private UserService userService;

//    @Autowired
//    private RedissonClient client;

    @PostMapping("/test")
    @WebLog(value = "111", data = "222")
    public Result<User> getUser(@RequestBody User user) {
        //Boolean i = userService.insertUser(user);
        Result<User> success = Result.success(JSON.toJSONString(user));
        return success;
    }

    @PostMapping("/test2")
    public Result<User> getUser2(@RequestBody User user) {
        Boolean i = userService.insertUser(user);
        return Result.success(JSON.toJSONString(user));
    }

    @PostMapping("/update")
    public User update(@RequestBody User user) {
        Boolean i = userService.updateUser(user);
        return user;
    }

    @PostMapping("/search")
    public List<User> search(User user) {
        return userService.search(user, User.class);
    }

    public static void main(String[] args) {
        byte[] allocation1, allocation2;
        allocation1 = new byte[53900 * 1024];
        allocation2 = new byte[9900 * 1024];
    }

    @Autowired
    private TestComponent testComponent;

    @GetMapping("/aaaaa")
    public String test() {
        return testComponent.test();
    }

//    @GetMapping("/redis/test")
//    public void redisTest() {
//        RLock lock = client.getLock("redisTest");
//        lock.lock();
//        try {
//            System.out.println(Thread.currentThread().getName() + "进入方法");
//            TimeUnit.SECONDS.sleep(10);
//        } catch (Exception e) {
//
//        } finally {
//            lock.unlock();
//            System.out.println(Thread.currentThread().getName() + "退出方法");
//        }
//    }
//
//    @GetMapping("/testMoney")
//    public String testMoney() {
//        RSemaphore money = client.getSemaphore("money");
//        try {
//            money.acquire();
//            return "ok";
//        } catch (Exception e) {
//            System.out.println(e);
//            return "no";
//        }
//    }

    @GetMapping("/test/head")
    public String headTest(@RequestHeader("h") String h) {
        return h;
    }
}
