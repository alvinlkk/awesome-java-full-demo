/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.zookeeper;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/7/31
 * @since 1.0
 **/
public class LeaderSelectorTest {

    // zk地址
    private static final String CONNECT_STR = "localhost:2181";
    // leader的path
    private static final String LEADER_PATH = "/app/leaderSelector";

    public static void main(String[] args) {
        List<CuratorFramework> clients = Lists.newArrayListWithCapacity(10);
        List<LeaderSelectorClient> leaderSelectorClients = Lists.newArrayListWithCapacity(10);
        try {
            //启动10个zk客户端，每几秒进行一次leader选举
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECT_STR, new ExponentialBackoffRetry(1000, 3));
                client.start();
                clients.add(client);
                LeaderSelectorClient exampleClient = new LeaderSelectorClient(client, LEADER_PATH, "client#" + i);
                leaderSelectorClients.add(exampleClient);
                exampleClient.start();
            }
            //sleep 以观察抢主过程
            Thread.sleep(Integer.MAX_VALUE);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            leaderSelectorClients.forEach(leaderSelectorClient -> {
                CloseableUtils.closeQuietly(leaderSelectorClient);
            });
            clients.forEach(client -> {
                CloseableUtils.closeQuietly(client);
            });


        }

    }

    static class LeaderSelectorClient extends LeaderSelectorListenerAdapter implements Closeable {
        private final String name;
        private final LeaderSelector leaderSelector;
        private final AtomicInteger leaderCount = new AtomicInteger(1);

        public LeaderSelectorClient(CuratorFramework client, String path, String name) {
            this.name = name;
            leaderSelector = new LeaderSelector(client, path, this);
            // 该方法能让客户端在释放leader权限后 重新加入leader权限的争夺中
            leaderSelector.autoRequeue();
        }

        public void start() throws IOException {
            leaderSelector.start();
        }

        @Override
        public void close() throws IOException {
            leaderSelector.close();
        }

        @Override
        public void takeLeadership(CuratorFramework client) throws Exception {
            // 抢到leader权限后sleep一段时间，并释放leader权限
            final int waitSeconds = (int) (5 * Math.random()) + 1;

            System.out.println(name + "成为leader.....");
            System.out.println(name + "成为leader的次数是" + leaderCount.getAndIncrement());
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
            } catch (InterruptedException e) {
                System.err.println(name + " was interrupted.");
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(name + "让出leader权限\n");
            }
        }
    }
}
