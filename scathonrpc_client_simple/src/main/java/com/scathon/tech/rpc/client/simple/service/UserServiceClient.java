package com.scathon.tech.rpc.client.simple.service;

import com.scathon.tech.rpc.client.DemoService;
import com.scathon.tech.rpc.common.annotations.RpcAutowired;
import com.scathon.tech.rpc.common.annotations.RpcServiceSubscriber;

/**
 * @author linhd
 */
@RpcServiceSubscriber
public class UserServiceClient {

    @RpcAutowired
    public DemoService demoService;
}

