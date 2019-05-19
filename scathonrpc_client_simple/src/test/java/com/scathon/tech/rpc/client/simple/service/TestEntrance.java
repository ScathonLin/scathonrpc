package com.scathon.tech.rpc.client.simple.service;


import com.scathon.tech.rpc.client.initializer.RpcClientAutoInitConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestEntrance {
    @Test
    public void testAnnotaionCapture() {
        //ApplicationContext context = new ClassPathXmlApplicationContext("classpath:rpcclient.xml");
        AnnotationConfigApplicationContext context1 =
                new AnnotationConfigApplicationContext(RpcClientAutoInitConfig.class);
        System.out.println();
    }
}
