package com.scathon.tech.rpc.server.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理发布了哪些RPC服务.
 *
 * @ClassName RpcServicePublishedHolder.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/4/27
 * @Version 1.0
 */
public final class RpcServicePublishedHolder {
    private static final Map<String, Object> SERVICE_REGIS_MAP = new ConcurrentHashMap<>();

    private RpcServicePublishedHolder() {
    }

    public static void registryRpcService(Map<String, Object> serviceMap) {
        serviceMap.forEach(SERVICE_REGIS_MAP::put);
    }

    public static Map<String, Object> getServiceRegisMap() {
        return SERVICE_REGIS_MAP;
    }
}
