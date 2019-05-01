package com.scathon.tech.rpc.registry.zookeeper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Zookeeper连接的配置的信息.
 *
 * @ClassName ZKConnectionConfiguration.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/4/27
 * @Version 1.0
 */
@Component
public final class ZookeeperConnConfig {

    /**
     * zookeeper 集群地址.
     */
    @Value("${zookeeper.cluster.addresses}")
    private String clusterAddrs = "127.0.0.1:2181";

    /**
     * zookeeper session 超时时间.
     */
    @Value("${zookeeper.session.timeout.ms}")
    private int sessionTimeout = 30000;

    private ZookeeperConnConfig() {
    }

    int getSessionTimeout() {
        return sessionTimeout;
    }

    String getClusterAddrs() {
        return clusterAddrs;
    }
}
