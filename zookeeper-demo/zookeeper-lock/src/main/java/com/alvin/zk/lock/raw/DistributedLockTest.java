package com.alvin.zk.lock.raw;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class DistributedLockTest {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributedLock lock1 = new DistributedLock();
        DistributedLock lock2 = new DistributedLock();

        new Thread(() -> {
            // 获取锁对象
            try {
                lock1.zkLock();
                System.out.println("线程 1 获取锁");
                Thread.sleep(5 * 1000);
                System.out.println("线程 1 释放锁");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock1.zkUnlock();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                lock2.zkLock();
                System.out.println("线程 2 获取锁");
                Thread.sleep(5 * 1000);

                System.out.println("线程 2 释放锁");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock2.zkUnlock();
            }
        }).start();
    }
}
