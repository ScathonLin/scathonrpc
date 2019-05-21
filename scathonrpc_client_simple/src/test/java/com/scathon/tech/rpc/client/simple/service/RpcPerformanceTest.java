package com.scathon.tech.rpc.client.simple.service;

import com.scathon.tech.rpc.client.initializer.RpcClientAutoInitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 性能测试.
 *
 * @ClassName RpcPerformanceTest.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RpcClientAutoInitConfig.class)
public class RpcPerformanceTest {
    @Autowired
    private UserServiceClient userServiceClient;


    @Test
    public void simpleTest() {
        System.out.println(userServiceClient.findUserById(11111));
    }

    /**
     * 单线程轮询方式.
     */
    @Test
    public void loopPerfTest() {
        int loopCount = 1000;
        System.out.println(userServiceClient != null);
        long start = System.currentTimeMillis();
        for (int i = 0; i < loopCount; i++) {
            System.out.println(userServiceClient.findUserById(11111));
            //System.out.println(userServiceClient.findOrderByUserInfo());
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println("=====>COST: " + cost + "ms");
        System.out.println("tps: " + (loopCount * 1.0 / (cost * 1.0 / 1000)));
    }

    /**
     * 多线程并发请求.
     */
    @Test
    public void concurrentPerfTest() {
        int loopNum = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(loopNum);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < loopNum; i++) {
                executorService.submit(() -> {
                    System.out.println(userServiceClient.findUserById(11111));
                    System.out.println(userServiceClient.findOrderByUserInfo());
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            long cost = System.currentTimeMillis() - start;
            System.out.println("cost time: " + cost + " ms");
            System.out.println("tps: " + (double) loopNum / ((double) cost / 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        System.exit(0);
    }

}
