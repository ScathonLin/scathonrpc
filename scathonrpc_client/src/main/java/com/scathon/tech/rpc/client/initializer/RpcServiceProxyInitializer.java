package com.scathon.tech.rpc.client.initializer;

import com.scathon.tech.rpc.client.netty.RpcClientBootstrap;
import com.scathon.tech.rpc.client.proxy.CglibProxyUtils;
import com.scathon.tech.rpc.client.proxy.RpcServiceCallProxy;
import com.scathon.tech.rpc.common.annotations.RpcAutowired;
import com.scathon.tech.rpc.common.annotations.RpcServiceSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcProxyInitializer.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
@Component
public class RpcServiceProxyInitializer implements InitializingBean, ApplicationContextAware, DisposableBean {
    private ApplicationContext context;
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceProxyInitializer.class);


    @Override
    public void afterPropertiesSet() throws Exception {
        //String pathPattern = "classpath*:com\\scathon\\tech\\rpc\\client\\**\\*.**";
        Map<String, Object> rpcSrvSubMap = context.getBeansWithAnnotation(RpcServiceSubscriber.class);

        for (Object obj : rpcSrvSubMap.values()) {
            String className = obj.getClass().getName();
            Class<?> clazz = Class.forName(className);
            Field[] fields = clazz.getDeclaredFields();
            List<Field> rpcSrvSubAnnoList = Arrays.stream(fields)
                    .filter(field -> Arrays.stream(field.getDeclaredAnnotations())
                            .anyMatch(anno -> anno.annotationType() == RpcAutowired.class))
                    .collect(Collectors.toList());
            for (Field field : rpcSrvSubAnnoList) {
                RpcServiceCallProxy proxy = RpcServiceCallProxy.getInstance();
                Object targetProxy = CglibProxyUtils.getProxyByInterfaceType(proxy, field.getType());
                field.setAccessible(true);
                field.set(obj, targetProxy);
            }
        }
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
