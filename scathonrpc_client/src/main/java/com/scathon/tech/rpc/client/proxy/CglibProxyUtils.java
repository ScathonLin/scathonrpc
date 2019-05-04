package com.scathon.tech.rpc.client.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * TODO Function The Class Is.
 *
 * @ClassName CglibProxyUtils.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class CglibProxyUtils {
    private CglibProxyUtils() {
    }

    public static Object getProxyByInterfaceType(MethodInterceptor proxy, Class<?> interfaceType) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(interfaceType);
        enhancer.setCallback(proxy);
        return enhancer.create();
    }
}
