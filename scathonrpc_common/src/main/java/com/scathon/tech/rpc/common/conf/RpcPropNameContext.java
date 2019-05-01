package com.scathon.tech.rpc.common.conf;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcPropNameContext.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
public class RpcPropNameContext {
    /**
     * 服务地址.
     */
    public static final String SERVICE_PROVIDER_IP_ADDRS = "server.provider.ips.addrs";
    /**
     * 注册中心组件类型（zk，eureka etc...）
     */
    public static final String REGISTER_COMPONENT_TYPE = "register.component.type";
    /**
     * zookeeper集群地址.
     */
    public static final String ZK_CLUSTER_ADDRS = "zookeeper.cluster.addresses";
    /**
     * zookeeper session超时时间配置项.
     */
    public static final String ZK_SESSION_TIMEOUT_MS = "zookeeper.session.timeout.ms";
}
