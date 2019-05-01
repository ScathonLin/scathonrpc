package com.scathon.tech.rpc.client.spring;

import com.scathon.tech.rpc.common.annotations.RpcServiceSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RpcServiceBeanInitializer implements InitializingBean, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceBeanInitializer.class);
    private ApplicationContext context;


    @Override
    public void afterPropertiesSet() {
        // start init some task....
        LOGGER.info("start initializing some task for rpc client...");
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(RpcServiceSubscriber.class);
        BeanDefinitionBuilder.genericBeanDefinition();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
