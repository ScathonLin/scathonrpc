package com.scathon.tech.rpc.client.simple.service;

import com.scathon.tech.rpc.client.initializer.RpcClientAutoInitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcPerformanceTest.class);
    @Autowired
    private UserServiceClient userServiceClient;

    @Test
    public void testCustomRegistry() {
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
}
