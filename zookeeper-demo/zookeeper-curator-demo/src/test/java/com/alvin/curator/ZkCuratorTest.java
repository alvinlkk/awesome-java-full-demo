/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.curator;

import java.nio.charset.StandardCharsets;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/7/3
 * @since 1.0
 **/
@Slf4j
public class ZkCuratorTest {

    private CuratorFramework curatorFramework;

    @Before
    public void init() {
        log.info("创建会话开始……………………");
        // 重试机制, 每个1秒钟重试1次，最多重试5次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5, 5000);
        // 定义客户端
        curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181", 30000, 60000, retryPolicy);
        // 建立连接
        curatorFramework.start();
    }

    @After
    public void close() {
        curatorFramework.close();
    }

    @Test
    public void testCreateNode() throws Exception {
        // 创建临时的，任何人都有权限的节点
        String result = curatorFramework.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/emNode", "hello".getBytes(StandardCharsets.UTF_8));
        log.info("创建节点: [{}]", result);
    }

    @Test
    public void testUpdateNode() throws Exception {
        // 创建节点
        curatorFramework.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/emNode", "hello".getBytes(StandardCharsets.UTF_8));
        // 不带版本修改节点
        Stat stat = curatorFramework.setData().forPath("/emNode", "hello1".getBytes(StandardCharsets.UTF_8));
        log.info("修改后节点版本: [{}]", stat.getVersion());

        // 带版本修改节点，版本不正确，报错
        stat = curatorFramework.setData().withVersion(10).forPath("/emNode", "hello1".getBytes(StandardCharsets.UTF_8));
        log.info("修改后节点版本: [{}]", stat.getVersion());
    }

    @Test
    public void testGetNode() throws Exception {
        // 创建节点
        curatorFramework.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/emNode", "hello".getBytes(StandardCharsets.UTF_8));
        // 直接查询
        byte[] data = curatorFramework.getData().forPath("/emNode");
        log.info("节点数据: [{}]", new String(data));

        // 同时获取节点属性，放到stat中
        Stat stat = new Stat();
        data = curatorFramework.getData().storingStatIn(stat).forPath("/emNode");
        log.info("节点数据: [{}]", new String(data));
        log.info("节点版本: [{}]", stat.getVersion());

        // 查询的时候注册监听
        data = curatorFramework.getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                log.info("事件监听: [{}]", event);
            }
        }).forPath("/emNode");
        log.info("节点数据: [{}]", new String(data));

        Thread.sleep(100000);
    }

    @Test
    public void testDeleteNode() throws Exception {
        // 创建节点
        curatorFramework.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/emNode", "hello".getBytes(StandardCharsets.UTF_8));

        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/emNode");
    }

    @Test
    public void testWatchCache() throws Exception {
        try {
            String workPath = "/aa/bb/cc";
            // 创建节点
            curatorFramework.create().creatingParentContainersIfNeeded()
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(workPath, "hello".getBytes(StandardCharsets.UTF_8));
            // 创建缓存
            CuratorCache curatorCache = CuratorCache.builder(curatorFramework, workPath).build();
            CuratorCacheListener curatorCacheListener = new CuratorCacheListener() {

                @Override
                public void event(Type type, ChildData oldData, ChildData data) {
                    log.info("%%%%%%%%%%%%%%%watch start……………………………………………………");
                    log.info("change type: [{}]", type);
                    log.info("ZNode节点状态改变, path={}", data.getPath());
                    log.info("ZNode节点状态改变,before: [{}], after: [{}]", oldData != null ? new String(oldData.getData()): null, new String(data.getData()));
                }
            };
            curatorCache.listenable().addListener(curatorCacheListener);
            curatorCache.start();

            // 第1次变更节点数据
            curatorFramework.setData().forPath("/aa/bb", "第1次更改内容".getBytes());
            Thread.sleep(1000);

            // 第2次变更节点数据
            curatorFramework.setData().forPath("/aa/bb/cc", "第2次更改内容".getBytes());

            Thread.sleep(1000);

            // 第3次创建新节点
            curatorFramework.create().creatingParentContainersIfNeeded()
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath("/aa/bb/dd", "hello".getBytes(StandardCharsets.UTF_8));
            Thread.sleep(1000);

            Thread.sleep(5000);
        } catch (Exception e) {
            log.error("创建Cache监听失败", e);
        } finally {
            // 删除节点
            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/aa");
        }
    }


}
