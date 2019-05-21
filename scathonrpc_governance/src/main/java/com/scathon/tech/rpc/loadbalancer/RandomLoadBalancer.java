package com.scathon.tech.rpc.loadbalancer;

import com.scathon.tech.rpc.registry.common.ServiceInfo;

import java.util.Random;

/**
 * 随机路由选择.
 *
 * @ClassName RandomLoadBalancer.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/21
 * @Version 1.0
 */
public class RandomLoadBalancer implements LoadBalancer {

    private static final Random RAND = new Random();

    @Override
    public ServiceInfo getServiceInfo(ServiceInfo remoteServiceInfo) {
        String addrListStr = remoteServiceInfo.getServiceAddrList();
        String[] addrList = addrListStr.split(",");
        int serviceNodeNum;
        if ((serviceNodeNum = addrList.length) == 0) {
            return null;
        }
        int serviceIndex = RAND.nextInt(serviceNodeNum);
        String targetIpAddr = addrList[serviceIndex];
        return ServiceInfo.builder().serviceName(remoteServiceInfo.getServiceName()).serviceAddrList(targetIpAddr).build();
    }
    
}
