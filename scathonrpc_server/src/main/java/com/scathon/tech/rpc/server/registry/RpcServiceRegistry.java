package com.scathon.tech.rpc.server.registry;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcServiceRegistry.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/4/27
 * @Version 1.0
 */
public final class RpcServiceRegistry {
    private static final RpcServiceRegistry INSTANCE = new RpcServiceRegistry();

    private static final Map<String, Object> SERVICE_REGIS_MAP = new ConcurrentHashMap<>();

    private RpcServiceRegistry() {
    }

    public static void registryRpcService(Map<String, Object> serviceMap) {
        serviceMap.forEach(SERVICE_REGIS_MAP::put);
    }

    public static Map<String, Object> getServiceRegisMap() {
        return SERVICE_REGIS_MAP;
    }
}
