package cn.com.bsfit.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZkWatchTest {

    private static final String ZK_ADDR = "10.100.1.14:2181";

    private static final Integer ZK_SESSION_TIMEOUT = 30000;

    private ZooKeeper zooKeeper = null;

    @Before
    public void init() throws IOException, InterruptedException {
        log.info("********************** start zk ..................");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        zooKeeper = new ZooKeeper(ZK_ADDR, ZK_SESSION_TIMEOUT, event -> {
            log.info("%%%%%%%%%%%%%%%%%%%%%触发了事件：[{}]", event);
            if (event.getType() == Watcher.Event.EventType.None) {   // 说明没有发生除了连接问题以外的事件
                switch (event.getState()) {
                    case SyncConnected:
                        log.info("客户端与服务器正常连接");
                        countDownLatch.countDown();
                        break;
                    case Disconnected:
                        log.info("客户端与服务器断开连接");
                        break;
                    case Expired:
                        log.info("会话SESSION超时");
                        break;
                    case AuthFailed:
                        log.info("身份认证失败");
                        break;
                }
            }
        });
        countDownLatch.await();
    }

    @After
    public void close() throws InterruptedException {
        zooKeeper.close();
        log.info("************************ close zk ..................");
    }


    private void syncCreateNode(String path, String data) throws InterruptedException, KeeperException {
        zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    @Test
    public void testGetWatch() throws InterruptedException, KeeperException {
        // 创建临时节点
        syncCreateNode("/watch", "aaa");

        byte[] data = zooKeeper.getData("/watch", true, new Stat());
        log.info("getData: [{}]", new String(data));

        Thread.sleep(100000L);
    }

    // 修改数据
    @Test
    public void testUpdateData() throws InterruptedException, KeeperException {
        // 创建临时节点
        zooKeeper.setData("/watch", "bbb".getBytes(), -1);
        Thread.sleep(10000L);
    }

    @Test
    public void testExists() throws KeeperException, InterruptedException {
        // 创建临时节点
        syncCreateNode("/watch", "aaa");
        // 重复使用，用完再注册一个新的
        Stat stat = zooKeeper.exists("/watch", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                switch (watchedEvent.getType()) {
                    case NodeCreated:
                        log.info("{}节点创建了", watchedEvent.getPath());
                        break;
                    case NodeDataChanged:
                        log.info("{}节点数据被修改了", watchedEvent.getPath());
                        break;
                    case NodeDeleted:
                        log.info("{}节点被删除了", watchedEvent.getPath());
                        break;
                }
                try {
                    // 重复监听的关键
                    zooKeeper.exists("/watch", this);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        if (stat != null) {
            log.info("version: " + stat.getVersion());
        }
        Thread.sleep(100000);
    }

    @Test
    public void testAddWatch() throws InterruptedException, KeeperException {
        // 创建临时节点
        syncCreateNode("/watch", "aaa");
        zooKeeper.addWatch("/watch", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                switch (event.getType()) {
                    case NodeCreated:
                        log.info("{}节点创建了", event.getPath());
                        break;
                    case NodeDataChanged:
                        log.info("{}节点数据被修改了", event.getPath());
                        break;
                    case NodeDeleted:
                        log.info("{}节点被删除了", event.getPath());
                        break;
                }
            }
        }, AddWatchMode.PERSISTENT);

        Thread.sleep(100000);
    }

}
