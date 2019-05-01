package com.scathon.tech.rpc.registry.zookeeper;

import com.alibaba.fastjson.JSONObject;
import com.scathon.tech.rpc.registry.ServiceRegister;
import com.scathon.tech.rpc.registry.common.ServiceInfo;
import com.scathon.tech.rpc.registry.common.ZookeeperConstant;
import com.scathon.tech.rpc.registry.exception.ServiceModifyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ZooKeeper 服务注册发现实现.
 *
 * @ClassName ZookeeperServiceRegister.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Component
public class ZookeeperServiceRegister implements ServiceRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceRegister.class);

    /**
     * 装配zookeeper.
     */
    @Autowired
    private ZookeeperClientHolder zkClientHolder;


    @Override
    public ServiceInfo registerService(ServiceInfo serviceInfo) {
        LOGGER.info("===>regiter new service, service info is : {}", JSONObject.toJSONString(serviceInfo));
        String serviceName = serviceInfo.getServiceName();
        String nodePath = ZookeeperConstant.ROOT_NODE + "/" + serviceName;
        ZooKeeper zkClient = zkClientHolder.getClient();

        try {
            Stat existState = zkClient.exists(ZookeeperConstant.ROOT_NODE, false);

            if (existState == null) {
                LOGGER.warn("===>zookeeper root node is not exists,start create it: {}", ZookeeperConstant.ROOT_NODE);
                zkClient.create(ZookeeperConstant.ROOT_NODE, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
                LOGGER.warn("create zookeeper root node succesfully...");
            }

            // paramters validation
            String srvAddrList = serviceInfo.getServiceAddrList();
            String serviceAddrList = StringUtils.isEmpty(srvAddrList) ? StringUtils.EMPTY : srvAddrList;

            LOGGER.debug("the service address list is : {}", serviceAddrList);
            Stat serviceNodeStat;
            if ((serviceNodeStat = zkClient.exists(nodePath, false)) == null) {
                zkClient.create(nodePath, serviceAddrList.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
            } else {
                zkClient.setData(nodePath, serviceAddrList.getBytes(), serviceNodeStat.getVersion() + 1);
            }

            LOGGER.debug("register service successfully,service name is : {}", serviceInfo.getServiceName());
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("found error while register new service , error msg is : {}", e.getMessage());
        }
        return serviceInfo;
    }

    @Override
    public ServiceInfo removeService(ServiceInfo serviceInfo) throws ServiceModifyException {
        return null;
    }

    @Override
    public List<ServiceInfo> retrieveService() {
        return null;
    }
}
