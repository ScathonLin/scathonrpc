package com.scathon.tech.rpc.server.simple.service;

import com.scathon.tech.rpc.common.annotations.RpcServicePublisher;
import com.scathon.tech.rpc.service.example.entity.User;
import com.scathon.tech.rpc.service.example.service.UserService;

/**
 * 业务实现类.
 *
 * @author scathonlin linhuadong.
 * @date 2019-04-27.
 */
@RpcServicePublisher(name = "userService")
public class UserServiceImpl implements UserService {
    @Override
    public User findUserById(int id) {
        return User.builder().id(1).username("linhd").build();
    }
}
