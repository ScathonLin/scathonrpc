package com.scathon.tech.rpc.loadbalancer;

import com.scathon.tech.rpc.registry.common.ServiceInfo;

/**
 * 负载均衡接口.
 *
 * @ClassName LoadBalancer.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/21
 * @Version 1.0
 */
public interface LoadBalancer {
    /**
     * 获取服务信息接口.
     *
     * @param remoteServiceInfo 从注册中心拉取下来的服务信息.
     * @return 将要使用的服务信息.
     */
    ServiceInfo getServiceInfo(ServiceInfo remoteServiceInfo);
}
