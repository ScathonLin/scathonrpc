package com.scathon.tech.rpc.registry.zookeeper;

import com.alibaba.fastjson.JSONObject;
import com.scathon.tech.rpc.common.conf.RpcProperties;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper Client Factory.
 *
 * @ClassName ZookeeperClientHolder.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/4/27
 * @Version 1.0
 */
@Component
public final class ZookeeperClientHolder {

    /**
     * zookeeper connection configuration.
     */
    @Autowired
    private RpcProperties rpcProperties;

    /**
     * zookeeper client.
     */
    private ZooKeeper zkClient;

    /**
     * 锁旗标.
     */
    private static final Object LOCK = new Object();

    /**
     * 等待zk连接成功的门闸.
     */
    private static final CountDownLatch SYNC_CONNECTED_FLAG = new CountDownLatch(1);

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClientHolder.class);

    /**
     * zk连接成功回调的监视器，创建成功，拉下门闸，放行代码.
     */
    private static final Watcher CONN_WATCHER = event -> {
        if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
            SYNC_CONNECTED_FLAG.countDown();
        }
    };

    private ZookeeperClientHolder() {
    }

    public ZooKeeper getClient() {
        LOGGER.info("start connect to zookeeper...conf : {}", JSONObject.toJSONString(rpcProperties));
        try {
            if (zkClient == null) {
                synchronized (LOCK) {
                    if (zkClient == null) {
                        zkClient = new ZooKeeper(rpcProperties.getClusterAddrs(), rpcProperties.getSessionTimeout(),
                                CONN_WATCHER);
                        SYNC_CONNECTED_FLAG.await();
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error("failed to connect to zookeeper, msg is : {}", e.getMessage());
        }
        return zkClient;
    }

    public ZooKeeper rebuild() {
        try {
            zkClient = new ZooKeeper(rpcProperties.getClusterAddrs(), rpcProperties.getSessionTimeout(), CONN_WATCHER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zkClient;
    }

}
