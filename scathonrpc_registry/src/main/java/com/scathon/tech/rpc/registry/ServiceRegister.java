package com.scathon.tech.rpc.registry;

import com.scathon.tech.rpc.registry.common.ServiceInfo;
import com.scathon.tech.rpc.registry.exception.ServiceModifyException;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ServiceRegister.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
public interface ServiceRegister {
    /**
     * 注册服务.
     *
     * @param serviceInfo 服务注册信息.
     * @return 注册服务信息.
     * @throws ServiceModifyException 服务变更异常.
     */
    ServiceInfo registerService(ServiceInfo serviceInfo) throws ServiceModifyException;

    /**
     * 下线服务，主动下线服务.
     *
     * @param serviceInfo 下线服务名称.
     * @return 下线的服务信息.
     */
    ServiceInfo removeService(ServiceInfo serviceInfo) throws ServiceModifyException;

    /**
     * 拉取服务注册信息.
     *
     * @return 服务注册信息列表.
     * @param serviceName 服务名称
     */
    ServiceInfo discoverService(String serviceName);

}
