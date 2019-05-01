package com.scathon.tech.rpc.registry;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ZookeeperClientTest.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
public class ZookeeperClientTest {
    private ZooKeeper zk;

    @Before
    public void init() throws IOException {
        zk = new ZooKeeper("127.0.0.1:2181", 30000, null);
    }

    @After
    public void close() throws InterruptedException {
        zk.close();
    }

    private Watcher watcher = event -> {
        System.out.println(event);
        if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
            System.out.println("node changed...." + event.getPath());
            try {
                updateChildrenInfo();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Test
    public void test() throws KeeperException, InterruptedException {
        zk.getChildren("/zktest", watcher);
        TimeUnit.SECONDS.sleep(120);
    }

    public void updateChildrenInfo() throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren("/zktest", watcher);
        System.out.println(children);
    }
}
