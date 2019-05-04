package com.scathon.tech.rpc.client.simple.service;

import com.scathon.tech.rpc.common.annotations.RpcAutowired;
import com.scathon.tech.rpc.common.annotations.RpcServiceSubscriber;
import com.scathon.tech.rpc.service.example.entity.User;
import com.scathon.tech.rpc.service.example.service.UserService;

/**
 * @author linhd
 */
@RpcServiceSubscriber
public class UserServiceClient {

    @RpcAutowired
    private UserService userService;

    public String findUserById(int id){
        User user = userService.findUserById(id);
        return user.toString();
    }

}

