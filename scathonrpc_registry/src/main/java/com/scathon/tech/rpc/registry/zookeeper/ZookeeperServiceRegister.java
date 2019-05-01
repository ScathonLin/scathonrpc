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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                        CreateMode.PERSISTENT);
            } else {
                String serviceNodes = new String(zkClient.getData(nodePath, null, null));

                // 将已经注册的服务的请求IP和端口查出来，利用新注册的服务的IP查询该IP是否已经注册过
                Set<String> serviceHasRegistered = Arrays.stream(serviceNodes.split(",")).collect(Collectors.toSet());
                String[] ipPortToPublish = srvAddrList.split(",");

                for (String itemToPublish : ipPortToPublish) {

                    // 判断IP和端口是否已经被别的服务绑定了.
                    if (serviceHasRegistered.contains(itemToPublish)) {
                        String[] socketInfo = itemToPublish.split(":");
                        LOGGER.error("service: {} to publish on {}:{} has to be binded to another service, this " +
                                        "address will be aborted...",
                                serviceName, socketInfo[0], socketInfo[1]);
                        continue;
                    }
                    serviceHasRegistered.add(itemToPublish);
                }

                String finalServiceNodesToPub = StringUtils.join(serviceHasRegistered, ",");
                zkClient.setData(nodePath, finalServiceNodesToPub.getBytes(), serviceNodeStat.getVersion());
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
