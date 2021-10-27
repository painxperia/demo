package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.domain.UserInfo;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.pojo.Role;
import com.example.demo.pojo.UserRole;
import com.example.demo.pojo.ZpUser;
import com.example.demo.strategy.TestAdaptor;
import com.example.demo.strategy.service.TestService;
import com.example.demo.util.mapstruct.UserConverter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 3
                , TimeUnit.SECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i <= 100; i++) {
            final int a = i;
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + a);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    log.error("eee:", e.getMessage());
                }
            });
        }
//        executor.execute(() -> {
//            for (int i = 0; i <= 100; i++) {
//                System.out.println(Thread.currentThread().getName() + ":" + i);
//                try {
////                    TimeUnit.SECONDS.sleep(1);
//                } catch (Exception e) {
//
//                }
//
//            }
//
//        });
//        IntStream.rangeClosed(1, 100).forEach(x -> {
//            executor.submit(() -> {
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName() + ":" + x);
//            });
//        });

    }

    @Autowired
    private TestAdaptor testAdaptor;

    @Test
    public void test22() {
        TestService testService = testAdaptor.getTestService(1);
        Integer code = testService.getCode();
        System.out.println(code);
    }

    @Test
    public void test2() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
//
//        Integer[] i = new Integer[10];
//        Integer[] integers = list.toArray(i);
//        System.out.println(Arrays.toString(integers));

        Integer set = list.set(0, 3);
        System.out.println(set);

    }

    @Test
    public void test3() {

//        Integer[] i1 = new Integer[]{1,2,3,4,5};
//
//        System.arraycopy(i1,0,i1,2,3);
//        System.out.println(Arrays.toString(i1));
        int MAXIMUM_CAPACITY = 1 << 30;
        int cap = 4;
        int n = cap - 1;
        n |= n >>> 1;
        System.out.println(n);
        n |= n >>> 2;
        System.out.println(n);
        n |= n >>> 4;
        System.out.println(n);
        n |= n >>> 8;
        System.out.println(n);
        n |= n >>> 16;
        System.out.println(n);
        System.out.println((n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1);
    }

    @Test
    public void test4() {


        ExecutorService executorService = Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(1);
        Executors.newScheduledThreadPool(2);


        Integer a1 = 2001;
        Integer a2 = 2001;
        if (a1 == a2) {
            //todo
        }
    }

    @Test
    public void test5() {
        int sum = IntStream.rangeClosed(1, 200).sum();
        System.out.println(sum);
    }

    @Test
    public void Test6() {
        System.out.println(0 % 5);
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void testRedis() {

//        redisTemplate.opsForHash().put("test","a","a");
//        redisTemplate.opsForHash().put("test","b","b");
        User user = new User();
        user.setId(1L);
        user.setAge(26);
        user.setName("Zpain");
        redisTemplate.opsForHash().put("user", user.getName(), user);
    }

    @Test
    public void test7() {
        try {
            User user = redisTemplate.<String, User>opsForHash().get("user", "Zpain");
            System.out.println(user);

            Result.<User>aaa();
        } catch (Exception e) {
            log.error("e:", e);
        }
    }

    @Test
    public void test8() {
        Function<Integer, String> function = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        };
        String apply = function.apply(2);
        System.out.println(apply);
    }

    @Autowired
    private UserFactory userFactory;

    @Test
    public void test9() throws Exception {
        User object = userFactory.getObject();
        System.out.println(JSON.toJSONString(object));
    }

    public void get(String str) {

    }

    public String get2(String str) {
        return null;
    }

    ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    @Test
    public void test10() {
        CompletableFuture.runAsync(this::testQueue);
        for (int i = 0; i < 11; i++) {
            try {
                queue.put(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(queue);
//        for (int i=0;i<11;i++){
//            Integer poll = queue.poll();
//            System.out.println("i:"+poll);
//        }
    }

    public void testQueue() {
        while (true) {
            if (queue.size() == 10) {
                for (int i = 0; i < 10; i++) {
                    Integer poll = queue.poll();
                    System.out.println("i:" + poll);
                }
            }
        }
    }

    @Test
    public void test11() {
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(10);
        for (int i = 0; i < 10; i++) {
            blockingQueue.offer(i);
        }
        System.out.println(blockingQueue);
        for (int i = 0; i < 10; i++) {
            Integer poll = blockingQueue.poll();
            System.out.println("i:" + poll);
        }
    }

    @Test
    public void test111() {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        for (int i = 0; i < 10; i++) {
            blockingQueue.offer(i);
        }
        System.out.println(blockingQueue);
        for (int i = 0; i < 10; i++) {
            Integer poll = blockingQueue.poll();
            System.out.println("i:" + poll);
        }
    }

    @Test
    public void test12() throws Exception {
        User user1 = null;
        System.out.println(user1);
        User user2 = new User();
        user2.setId(1L);
        user2.setName("111");
        User user = Optional.ofNullable(user1).orElse(user2);
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void test13() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnirestException {

//        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
//            @Override
//            public boolean isTrusted(X509Certificate[] chain, String authType) {
//                return true;
//            }
//        }).build();
//        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
//        Unirest.setHttpClient(client);
//        Map<String, Object> map = new HashMap<>();
//        GenericType<User> user = new GenericType<User>() {
//        };
        try {
            HttpResponse<String> stringHttpResponse = Unirest.post("https://sbpks-dv1.cisco.com/api/security-file-test/?project_name=TEST&security_file=ssl_key_pwd")
                    .header("Authorization", "Bearer PBvK33KYyEW3uqtseNgxDpBdaZV3")
                    .asString();
            log.info("response:{}", JSON.toJSONString(stringHttpResponse));
        } catch (Exception e) {
            log.error("e:", e);
        }
    }

    @Test
    public void test14() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("111");
        threadLocal.set("333");
        System.out.println(threadLocal.get());
        CompletableFuture.runAsync(() -> {
            threadLocal.set("222");
            System.out.println(threadLocal.get());
        });
    }

    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(20);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 30; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("threadNum:" + threadNum);
                    TimeUnit.SECONDS.sleep(1);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("e:", e);
                }
            });
        }
        executorService.shutdown();
        log.info("end");
    }

    @Test
    public void test15() {

//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setName("a" + i);
//            user.setAge(i);
//            redisTemplate.opsForList().leftPush("list", user);
//        }
//        System.out.println(redisTemplate.opsForList()
//                .range("list", 0, -1));
        List<User> list = redisTemplate.opsForList()
                .range("list", 0, -1).stream().map( u -> (User) u ).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test16(){
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setAge(12);
        redisTemplate.opsForValue().set("u",JSON.toJSONString(user, SerializerFeature.WriteClassName));
    }

    @Test
    public void test17(){
        ZpUser zpUser = new ZpUser();
        zpUser.setUsername("111");
        UserInfo userInfo = UserConverter.INSTANCE.toUser(zpUser);
        System.out.println(JSON.toJSONString(userInfo));

    }


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void test18() {
//        List<ZpUser> zpUsers = userMapper.selectList(null);
        QueryWrapper<ZpUser> queryWrapper = new QueryWrapper<ZpUser>();
        queryWrapper.eq("username","user1");
        ZpUser zpUser = userMapper.selectOne(queryWrapper);
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",zpUser.getId());
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);

        Role role = roleMapper.selectById(userRole.getRoleId());
        List<String> list = new ArrayList<>();

        list.stream().map( l -> l ).distinct().collect(Collectors.toList());

        list.add("ROLE_"+role.getRoleName());
        UserInfo userInfo = UserConverter.INSTANCE.toUser(zpUser);
        userInfo.setRoles(list);
        log.info("list:{}",JSON.toJSONString(userInfo));
    }




}
