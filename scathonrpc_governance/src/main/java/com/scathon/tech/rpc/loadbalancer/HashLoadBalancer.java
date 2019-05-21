package com.scathon.tech.rpc.loadbalancer;

import com.scathon.tech.rpc.registry.common.ServiceInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * Hash 负载均衡.
 *
 * @ClassName HashLoadBalancer.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/21
 * @Version 1.0
 */
public class HashLoadBalancer implements LoadBalancer {
    @Override
    public ServiceInfo getServiceInfo(ServiceInfo remoteServiceInfo) {
        int seed = (int) (remoteServiceInfo.hashCode() + System.currentTimeMillis());
        String serviceAddrList = remoteServiceInfo.getServiceAddrList();
        if (StringUtils.isEmpty(serviceAddrList)) {
            return null;
        }
        String[] addrList = serviceAddrList.split(",");
        int serviceNodeNum;
        if ((serviceNodeNum = addrList.length) == 0) {
            return null;
        }
        int hashIndex = seed % serviceNodeNum;
        return ServiceInfo.builder().serviceAddrList(addrList[hashIndex]).serviceName(remoteServiceInfo.getServiceName()).build();
    }
}
