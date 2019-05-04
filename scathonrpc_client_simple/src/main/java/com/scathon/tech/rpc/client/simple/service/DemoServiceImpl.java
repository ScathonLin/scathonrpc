package com.scathon.tech.rpc.client.simple.service;

/**
 * TODO Function The Class Is.
 *
 * @ClassName DemoServiceImpl.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public class DemoServiceImpl implements DemoService{
    @Override
    public String hello(String name) {
        return "hello:" + name;
    }
}
