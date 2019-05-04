package com.scathon.tech.rpc.client.simple.service;

import com.scathon.tech.rpc.client.spring.RpcClientBeanConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO Function The Class Is.
 *
 * @ClassName BeanRegistryTest.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RpcClientBeanConfiguration.class)
public class BeanRegistryTest {
    @Autowired
    private UserServiceClient userServiceClient;

    @Test
    public void testCustomRegistry() {
        System.out.println(userServiceClient != null);
        System.out.println(userServiceClient.findUserById(11111));
    }
}
