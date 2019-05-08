package com.scathon.tech.rpc.client.simple.service;

import com.scathon.tech.rpc.common.annotations.RpcAutowired;
import com.scathon.tech.rpc.common.annotations.RpcServiceSubscriber;
import com.scathon.tech.rpc.service.example.entity.User;
import com.scathon.tech.rpc.service.example.service.OrderService;
import com.scathon.tech.rpc.service.example.service.UserService;

/**
 * @author linhd
 */
@RpcServiceSubscriber
public class UserServiceClient {

    @RpcAutowired
    private UserService userService;

    @RpcAutowired
    private OrderService orderService;

    public String findUserById(int id) {
        User user = userService.findUserById(id);
        return user.toString();
    }

    public String findOrderByUserInfo() {
        User user = User.builder().username("hello").id(888).build();
        return orderService.findOrderByUserInfo(user, 666, "999");
    }

}

