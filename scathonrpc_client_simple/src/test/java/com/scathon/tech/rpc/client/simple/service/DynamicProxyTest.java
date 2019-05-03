package com.scathon.tech.rpc.client.simple.service;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO Function The Class Is.
 *
 * @ClassName DynamicProxyTest.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        //UserService userService = (UserService) Proxy.newProxyInstance(DynamicProxyTest.class.getClassLoader(),
        //        new Class<?>[]{UserService.class},
        //        new UserServiceProxy());
        //String userById = userService.findUserById(111);
        //System.out.println(userById);
        testCglib();
    }
    private static void testCglib(){
        CglibProxy proxy = new CglibProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(proxy);
        UserService userService = (UserService) enhancer.create();
        userService.findUserById(111);
    }
}

class CglibProxy implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("start");
        System.out.println("end");
        return null;
    }
}

class UserServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start call");
        //Object invoke = method.invoke(proxy, args);
        System.out.println("adter call");
        return null;
    }
}

interface UserService {
    String findUserById(int id);
}

class UserServiceImpl implements UserService{

    @Override
    public String findUserById(int id) {
        return "id:" + id;
    }
}