package cn.com.bsfit.zk;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZookeeperDemo {

    private static final String ZK_ADDR = "10.100.1.14:2181";

    private static final Integer ZK_SESSION_TIMEOUT = 30000;

    private ZooKeeper zooKeeper = null;

    @Before
    public void init() throws IOException, InterruptedException {
        log.info("********************** start zk ..................");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        zooKeeper = new ZooKeeper(ZK_ADDR, ZK_SESSION_TIMEOUT, event -> {
            log.info("%%%%%%%%%%%%%%%%%%%%%触发了事件：[{}]", event);
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }

    @After
    public void close() throws InterruptedException {
        zooKeeper.close();
        log.info("************************ close zk ..................");
    }

    @Test
    public void testCreate() throws InterruptedException, KeeperException {
        // 创建一个持久节点，对所有用户开放
        zooKeeper.create("/node1", "hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建一个临时的有序节点，权限模式为对指定ip开放
        Id ip = new Id("ip", "10.100.1.100");
        zooKeeper.create("/user", "u00001".getBytes(), Collections.singletonList(new ACL(ZooDefs.Perms.ALL, ip)), CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    // 异步创建节点
    @Test
    public void testCreateAsync() throws InterruptedException {
        zooKeeper.create("/path2", "hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,  new AsyncCallback.StringCallback(){

            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                log.info("rc: [{}]", rc);  // 0代表成功了
                log.info(path);  // 传进来的，添加的节点
                log.info(name);   // 真正查到的节点的名字
                log.info(ctx.toString());  // 上下文参数，ctx传进来的东西
                log.info("create node success！");
            }
        }, "ctx" );

        Thread.sleep(1000);
    }

    @Test
    public void testGet() throws InterruptedException, KeeperException {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/node1", false, stat);
        log.info("获取到的数据是：" + new String(data));
        log.info("当前节点的版本：" + stat.getVersion());
    }
    @Test
    public void testGetAsync() throws InterruptedException, KeeperException {
        zooKeeper.getData("/node1", null, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] bytes, Stat stat) {
                log.info("rc: " + rc);
                log.info(path);
                log.info(new String(bytes));
                log.info("version: " + stat.getVersion());
            }
        }, null);
        Thread.sleep(1000);
    }

    @Test
    public void testSetData() throws InterruptedException, KeeperException {
        Stat stat = zooKeeper.setData("/node1", "alvin".getBytes(), -1);  // 返回状态信息
        log.info(stat.toString());  // 将状态信息打印
        log.info("当前版本号" + stat.getVersion());
        log.info("节点创建时间" + stat.getCtime());
        log.info("节点修改时间" + stat.getMtime());
    }

    @Test
    public void testSetDataAsync() throws InterruptedException, KeeperException {
        zooKeeper.setData("/node1", "alvin2".getBytes(), 1, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                log.info("rc" + rc);  // 0 代表修改成功
                log.info(path); // 输入的节点路径
                log.info("version " + stat.getVersion()); // 当前版本
            }
        }, null);  // 返回状态信息

        Thread.sleep(1000);
    }

    @Test
    public void testDelete() throws InterruptedException, KeeperException {
        zooKeeper.delete("/node1", -1);  // 如果节点不存在，会删除失败
        zooKeeper.delete("/node5/child1", -1);  // 如果节点不存在，会删除失败

    }

    @Test
    public void testDeleteAsync() {
        zooKeeper.delete("/node1", -1, new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                log.info("rc:" + rc);
                log.info(path);
            }
        }, "ctx");
    }

    private void syncCreateNode(String path, String data) throws InterruptedException, KeeperException {
        zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void testGetChild() throws InterruptedException, KeeperException {
        syncCreateNode("/a", "hello");
        syncCreateNode("/a/b", "hello");
        syncCreateNode("/a/c", "hello");

        List<String> children = zooKeeper.getChildren("/a", false);
        log.info("********* children: [{}]", children);
    }

    @Test
    public void testExist() throws InterruptedException, KeeperException {
        syncCreateNode("/alvin1", "hello");
        Stat stat = zooKeeper.exists("/alvin1", false);
        log.info("stat: [{}]", stat);

        log.info("delete node /alvin1 ......");
        zooKeeper.delete("/alvin1", -1);
        stat = zooKeeper.exists("/alvin1", false);
        log.info("stat: [{}]", stat);
    }
}
