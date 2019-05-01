package com.scathon.tech.rpc.service.example.service;

import com.scathon.tech.rpc.service.example.entity.User;

public interface UserService {
    User findUserById(int id);
}
