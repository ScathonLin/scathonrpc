package com.scathon.tech.rpc.common.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcProperties.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Component
public class RpcProperties {
    /**
     * zookeeper 集群地址.
     */
    @Value("${zookeeper.cluster.addresses}")
    @Getter
    private String clusterAddrs = "127.0.0.1:2181";

    /**
     * zookeeper session 超时时间.
     */
    @Value("${zookeeper.session.timeout.ms}")
    @Getter
    private int sessionTimeout = 30000;

    /**
     * 注册中心技术选型，目前是zookeeper.
     */
    @Value("${register.component.type}")
    @Getter
    private String registerComponentType;

    /**
     * 服务绑定的IP端口连接串.
     */
    @Value("${server.provider.ips.addrs}")
    @Getter
    private String serverProviderIpsAddrs;

}
