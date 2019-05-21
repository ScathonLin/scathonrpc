package com.scathon.tech.rpc.client.proxy;

import com.scathon.tech.rpc.client.netty.RpcClientBootstrap;
import com.scathon.tech.rpc.common.annotations.RpcService;
import com.scathon.tech.rpc.common.entity.RequestMessage;
import com.scathon.tech.rpc.common.entity.ResponseMessage;
import com.scathon.tech.rpc.registry.ServiceRegister;
import com.scathon.tech.rpc.registry.common.ServiceInfo;
import com.scathon.tech.rpc.registry.zookeeper.ZookeeperServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
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
    private static final ServiceRegister REGISTER = ZookeeperServiceRegister.getInstance();

    private RpcServiceCallProxy() {
    }

    public static RpcServiceCallProxy getInstance() {
        return INSTANCE;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {

        LOGGER.info("===>start package request data...");

        // 封装request对象.
        String methodName = method.getName();
        RequestMessage reqMessage = new RequestMessage();
        reqMessage.setRequestUUID(UUID.randomUUID().toString());
        String serviceName = method.getDeclaringClass().getAnnotation(RpcService.class).name();
        reqMessage.setServiceName(serviceName);
        reqMessage.setMethodName(methodName);
        reqMessage.setParamTypes(method.getParameterTypes());
        reqMessage.setParamObjs(params);

        // 服务发现.
        ServiceInfo serviceInfo = REGISTER.discoverService(serviceName);
        LOGGER.info("===>service discover result: {}", serviceInfo.getServiceAddrList());

        RpcClientBootstrap bootstrap = RpcClientBootstrap.getInstance();
        // 调用RPC服务.
        ResponseMessage rsp = bootstrap.callRemoteService(reqMessage, serviceInfo);
        if (rsp == null) {
            throw new RuntimeException("some unknow runtime exception found...");
        }
        Object bodyObj = rsp.getRespBody();
        Class<?> returnType = method.getReturnType();
        if (returnType.isInstance(bodyObj)) {
            return returnType.cast(bodyObj);
        } else {
            throw new ClassCastException("resBody " + bodyObj + " is not the instance of " + returnType.getSimpleName());
        }
    }
}
