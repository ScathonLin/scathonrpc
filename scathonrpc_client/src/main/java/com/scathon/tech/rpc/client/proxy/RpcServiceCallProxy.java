package com.scathon.tech.rpc.client.proxy;

import com.scathon.tech.rpc.client.cache.LockCache;
import com.scathon.tech.rpc.client.netty.RpcClientBootstrap;
import com.scathon.tech.rpc.common.annotations.RpcService;
import com.scathon.tech.rpc.common.annotations.RpcServiceSubscriber;
import com.scathon.tech.rpc.common.entity.RequestMessage;
import com.scathon.tech.rpc.common.entity.ResponseMessage;
import com.scathon.tech.rpc.registry.ServiceRegister;
import com.scathon.tech.rpc.registry.common.ServiceInfo;
import com.scathon.tech.rpc.registry.zookeeper.ZookeeperServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

/**
 * RPC 服务代理类.
 *
 * @ClassName RpcServiceCallProxy.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public final class RpcServiceCallProxy implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceCallProxy.class);
    private static final RpcServiceCallProxy INSTANCE = new RpcServiceCallProxy();
    private static final ServiceRegister REGISTER = ZookeeperServiceRegister.getINSTANCE();

    private RpcServiceCallProxy() {
    }

    public static RpcServiceCallProxy getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {

        // 封装request对象.
        String methodName = method.getName();
        RequestMessage reqMsg = new RequestMessage();
        reqMsg.setRequestUUID(UUID.randomUUID().toString());
        String serviceName = method.getDeclaringClass().getAnnotation(RpcService.class).name();
        reqMsg.setServiceName(serviceName);
        reqMsg.setFunctionName(methodName);
        reqMsg.setParameterTypes(method.getParameterTypes());
        reqMsg.setParameters(params);

        ServiceInfo serviceInfo = REGISTER.discoverService(serviceName);
        RpcClientBootstrap bootstrap = RpcClientBootstrap.getINSTANCE();

        LockCache.push(reqMsg.getRequestUUID());
        ResponseMessage rsp = bootstrap.callRemoteService(reqMsg, serviceInfo);
        System.out.println("*****************************");
        return rsp.getRespBody();
    }
}
