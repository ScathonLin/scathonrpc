package com.scathon.tech.rpc.service.example.service;

import com.scathon.tech.rpc.common.annotations.RpcService;
import com.scathon.tech.rpc.service.example.entity.User;

@RpcService(name = "userService")
public interface UserService {
    User findUserById(int id);
}
