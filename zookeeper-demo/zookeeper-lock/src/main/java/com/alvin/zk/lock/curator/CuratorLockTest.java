package com.alvin.zk.lock.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorLockTest {

    private String connectString = "10.100.1.14:2181";

    private String rootNode = "/locks";

    public static void main(String[] args) {

        new CuratorLockTest().testLock();

    }

    public void testLock() {
        // 分布式锁1
        InterProcessLock lock1 = new InterProcessMutex(getCuratorFramework(), rootNode);
        // 分布式锁2
        InterProcessLock lock2 = new InterProcessMutex(getCuratorFramework(), rootNode);
        // 第一个线程
        new Thread(() -> {
            // 获取锁对象
            try {
                lock1.acquire();
                System.out.println("线程 1 获取锁");
                // 测试锁重入
                lock1.acquire();
                System.out.println("线程 1 再次获取锁");
                Thread.sleep(5 * 1000);
                lock1.release();
                System.out.println("线程 1 释放锁");
                lock1.release();
                System.out.println("线程 1 再次释放锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 第二个线程
        new Thread(() -> {
            // 获取锁对象
            try {
                lock2.acquire();
                System.out.println("线程 2 获取锁");
                // 测试锁重入
                lock2.acquire();
                System.out.println("线程 2 再次获取锁");
                Thread.sleep(5 * 1000);
                lock2.release();
                System.out.println("线程 2 释放锁");
                lock2.release();
                System.out.println("线程 2 再次释放锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public CuratorFramework getCuratorFramework() {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString).connectionTimeoutMs(2000)
                .sessionTimeoutMs(2000)
                .retryPolicy(new ExponentialBackoffRetry(3000, 3)).build();
        // 连接
        client.start();
        System.out.println("zookeeper 初始化完成...");
        return client;
    }
}
