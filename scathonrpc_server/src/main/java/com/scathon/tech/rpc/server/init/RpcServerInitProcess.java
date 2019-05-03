package com.scathon.tech.rpc.server.init;

import com.alibaba.fastjson.JSONObject;
import com.scathon.tech.rpc.common.annotations.RpcServicePublisher;
import com.scathon.tech.rpc.common.conf.RpcProperties;
import com.scathon.tech.rpc.registry.ServiceRegister;
import com.scathon.tech.rpc.registry.common.ServiceInfo;
import com.scathon.tech.rpc.registry.common.ServiceRoleEnum;
import com.scathon.tech.rpc.registry.exception.ServiceModifyException;
import com.scathon.tech.rpc.server.netty.RpcBootstrapServer;
import com.scathon.tech.rpc.server.registry.RpcServicePublishedHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rpc 服务端初始化逻辑.
 *
 * @ClassName RpcServerInitProcess.
 * @Description Rpc Server Initializing.
 * @Author linhd eng:ScathonLin
 * @Date 2019/4/27
 * @Version 1.0
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class RpcServerInitProcess implements InitializingBean, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerInitProcess.class);
    @Autowired
    private RpcProperties rpcProperties;
    @Autowired
    private ServiceRegister serviceRegister;
    @Autowired
    private RpcBootstrapServer rpcServer;

    @Override
    public void afterPropertiesSet() {
        LOGGER.info("start netty rpc engine...");
        // 拉起netty服务端，接受RPC请求，对外提供服务.
        rpcServer.bootstrap();
        LOGGER.info("netty rpc engine start successfully...");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {

        // 扫描RpcServicePublisher 修饰的类，将注解中的name属性和被修饰的对象做一个映射map.
        Map<String, Object> publisherServiceInstances = context.getBeansWithAnnotation(RpcServicePublisher.class);

        // 预处理被RpcServicePublisher修饰的类，将用其注解中指定的名称作为key，对象实例作为value做一个map.
        Map<String, Object> serviceNameToObjMap = publisherServiceInstances.entrySet().stream().map(entry -> {
            Object obj = entry.getValue();
            RpcServicePublisher rpcSrvPubAnno = obj.getClass().getDeclaredAnnotation(RpcServicePublisher.class);
            String serviceName = rpcSrvPubAnno.name();
            return Pair.of(serviceName, obj);
        }).collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

        // 缓存RPC服务.
        RpcServicePublishedHolder.registryRpcService(serviceNameToObjMap);

        // 注册服务.
        LOGGER.info("services need to publish are : {}", JSONObject.toJSONString(publisherServiceInstances.keySet()));
        serviceNameToObjMap.forEach((serviceName, serviceObj) -> {
            String serviceAddrList = rpcProperties.getServerProviderIpsAddrs();
            try {
                serviceRegister.registerService(ServiceInfo.builder().serviceAddrList(serviceAddrList).serviceName(serviceName).serviceRole(ServiceRoleEnum.SERVICE_PROVIDER).build());
            } catch (ServiceModifyException e) {
                LOGGER.error("failed to register service,service name is : {}, error msg is : {}", serviceName,
                        e.getMessage());
            }
        });
    }
}
