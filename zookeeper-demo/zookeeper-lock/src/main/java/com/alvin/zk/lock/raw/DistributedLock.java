package com.alvin.zk.lock.raw;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

public class DistributedLock {

    private final String connectString = "10.100.1.176:2281";

    private final int sessionTimeout = 2000;

    private final ZooKeeper zk;

    private final String rootNode = "lock";

    private final String subNode = "seq-";

    private String waitPath;

    // 当前client创建的子节点
    private String currentNode;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private final CountDownLatch waitDownLatch = new CountDownLatch(1);

    public DistributedLock() throws IOException, InterruptedException, KeeperException {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 如果连接建立时,唤醒 wait 在该 latch 上的线程
                if(event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }

                //  发生了 waitPath 的删除事件
                if(event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                    waitDownLatch.countDown();
                }
            }
        });

        // 等待连接建立，因为连接建立时异步过程
        countDownLatch.await();
        // 获取根节点
        Stat stat = zk.exists("/" + rootNode, false);
        // 如果根节点不存在，则创建根节点
        if(stat == null) {
            System.out.println("创建根节点");
            zk.create("/" + rootNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public void zkLock() {
        try {
            // 在根节点创建临时顺序节点
            currentNode = zk.create("/" + rootNode + "/" + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            // 获取子节点
            List<String> childrenNodes = zk.getChildren("/" + rootNode, false);
            // 如果只有一个子节点，说明是当前节点，直接获得锁
            if(childrenNodes.size() == 1) {
                return;
            } else {
                //对根节点下的所有临时顺序节点进行从小到大排序
                Collections.sort(childrenNodes);
                //当前节点名称
                String thisNode = currentNode.substring(("/" + rootNode + "/").length());
                //获取当前节点的位置
                int index = childrenNodes.indexOf(thisNode);
                if (index == -1) {
                    System.out.println("数据异常");
                } else if (index == 0) {
                    // index == 0, 说明 thisNode 在列表中最小, 当前client 获得锁
                    return;
                } else {
                    // 获得排名比 currentNode 前 1 位的节点
                    this.waitPath = "/" + rootNode + "/" + childrenNodes.get(index - 1);
                    // 在 waitPath节点上注册监听器, 当 waitPath 被删除时,zookeeper 会回调监听器的 process 方法
                    zk.getData(waitPath, true, new Stat());
                    //进入等待锁状态
                    waitDownLatch.await();
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void zkUnlock() {
        try {
            zk.delete(this.currentNode, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
