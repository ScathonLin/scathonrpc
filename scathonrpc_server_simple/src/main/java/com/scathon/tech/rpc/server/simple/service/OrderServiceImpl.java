package com.scathon.tech.rpc.server.simple.service;

import com.alibaba.fastjson.JSONObject;
import com.scathon.tech.rpc.common.annotations.RpcServicePublisher;
import com.scathon.tech.rpc.service.example.entity.User;
import com.scathon.tech.rpc.service.example.service.OrderService;

/**
 * TODO Function The Class Is.
 *
 * @ClassName OrderServiceImpl.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/8
 * @Version 1.0
 */
@RpcServicePublisher(name = "orderService")
public class OrderServiceImpl implements OrderService {
    @Override
    public String findOrderByUserInfo(User user, int paramInt, String paramString) {
        return JSONObject.toJSONString(user) + paramInt + paramString;
    }
}
