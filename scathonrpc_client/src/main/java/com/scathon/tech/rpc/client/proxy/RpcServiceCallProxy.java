package com.scathon.tech.rpc.client.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcServiceCallProxy.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public final class RpcServiceCallProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // TODO netty 调用远程服务并返回结果.
        System.out.println("*****************************");
        return null;
    }
}
