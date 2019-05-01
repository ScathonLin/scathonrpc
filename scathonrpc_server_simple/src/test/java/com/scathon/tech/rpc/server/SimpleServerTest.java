package com.scathon.tech.rpc.server;

import com.scathon.tech.rpc.common.init.RpcCommonBeanConfig;
import com.scathon.tech.rpc.registry.init.RegistryInitConfig;
import com.scathon.tech.rpc.registry.zookeeper.ZookeeperClientHolder;
import com.scathon.tech.rpc.server.beanconfig.ServerBeanConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * TODO Function The Class Is.
 *
 * @ClassName SimpleServerTest.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
public class SimpleServerTest {
    @Test
    public void test() throws InterruptedException {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ServerBeanConfig.class, RpcCommonBeanConfig.class, RegistryInitConfig.class);
        System.out.println("-0-0---");
    }
}
