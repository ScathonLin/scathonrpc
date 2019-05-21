package com.scathon.tech.rpc.registry.zookeeper;

import com.scathon.tech.rpc.common.conf.RpcConfig;
import com.scathon.tech.rpc.common.conf.RpcPropNameContext;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public final class ZookeeperClientHolder {

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

    private static final ZookeeperClientHolder INSTANCE = new ZookeeperClientHolder();

    public static ZookeeperClientHolder getInstance() {
        return INSTANCE;
    }

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
        try {
            if (zkClient == null) {
                synchronized (LOCK) {
                    if (zkClient == null) {
                        RpcConfig config = RpcConfig.getInstance();
                        String zkAddrList = config.getProperty(RpcPropNameContext.ZK_CLUSTER_ADDRS);
                        int sessionTimeout =
                                Integer.parseInt(config.getProperty(RpcPropNameContext.ZK_SESSION_TIMEOUT_MS));
                        zkClient = new ZooKeeper(zkAddrList, sessionTimeout,
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
            RpcConfig config = RpcConfig.getInstance();
            String zkAddrList = config.getProperty(RpcPropNameContext.ZK_CLUSTER_ADDRS);
            int sessionTimeout =
                    Integer.parseInt(config.getProperty(RpcPropNameContext.ZK_SESSION_TIMEOUT_MS));
            zkClient = new ZooKeeper(zkAddrList, sessionTimeout,
                    CONN_WATCHER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zkClient;
    }

}
