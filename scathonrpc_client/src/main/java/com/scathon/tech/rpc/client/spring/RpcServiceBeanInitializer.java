package com.scathon.tech.rpc.client.spring;

import com.scathon.tech.rpc.client.netty.RpcClientBootstrap;
import com.scathon.tech.rpc.common.annotations.RpcServiceSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@DependsOn(value = "rpcProxyInitializer")
public class RpcServiceBeanInitializer implements InitializingBean, ApplicationContextAware, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceBeanInitializer.class);
    private ApplicationContext context;


    @Override
    public void afterPropertiesSet() {
        // start init some task....
        LOGGER.info("start initializing some task for rpc client...");
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(RpcServiceSubscriber.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        LOGGER.warn("context is being closed, start release netty group....");
        // 释放netty group.
        RpcClientBootstrap.closeGroupGracefully();
        LOGGER.warn("complete release netty group...");
    }
}
