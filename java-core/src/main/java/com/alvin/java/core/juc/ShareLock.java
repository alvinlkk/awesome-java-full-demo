package com.alvin.java.core.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>共享锁例子：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/14  20:54
 * @version: 1.0.0
 */
@Slf4j
public class ShareLock {

    /**
     * 共享锁帮助类
     */
    private static class ShareSync extends AbstractQueuedSynchronizer {

        private final int lockCount;

        /**
         * 创建共享锁帮助类，最多有count把共享锁，超过了则阻塞
         *
         * @param count 共享锁数量
         */
        public ShareSync(int count) {
           this.lockCount = count;
        }

        /**
         * 尝试获取共享锁
         *
         * @param arg 每次获取锁的数量
         * @return 返回正数，表示后续其他线程获取共享锁可能成功； 返回0，表示后续其他线程无法获取共享锁；返回负数，表示当前线程获取共享锁失败
         */
        @Override
        protected int tryAcquireShared(int arg) {
            // 自旋
            for (;;) {
                int c = getState();
                // 如果持有锁的数量大于指定数量，返回-1，线程进入阻塞
                if(c >= lockCount) {
                    return -1;
                }
                int nextc = c + 1;
                // cas设置成功，返回1，获取到共享锁
                if (compareAndSetState(c, nextc)) {
                    return 1;
                }
            }
        }

        /**
         * 尝试释放共享锁
         *
         * @param arg 释放锁的数量
         * @return 如果释放后允许唤醒后续等待结点返回true，否则返回false
         */
        @Override
        protected boolean tryReleaseShared(int arg) {
            // 自旋操作
            for (; ; ) {
                int c = getState();
                // 如果没有锁了
                if (c == 0) {
                    return false;
                }
                // 否则锁量-1
                int nextc = c - 1;
                // cas修改状态
                if (compareAndSetState(c, nextc)) {
                    return true;
                }
            }
        }
    }

    private final ShareSync sync;

    public ShareLock(int count) {
        this.sync = new ShareSync(count);
    }

    /**
     * 加共享锁
     */
    public void lockShare() {
        sync.acquireShared(1);
    }

    /**
     * 释放共享锁
     */
    public void releaseShare() {
        sync.releaseShared(1);
    }

    public static void main(String[] args) throws InterruptedException {
        ShareLock shareLock = new ShareLock(3);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                shareLock.lockShare();
                try {
                    log.info("lock success");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    shareLock.releaseShare();
                    log.info("release success");
                }

            }, "thread-" + i).start();
        }
        Thread.sleep(10000);
    }
}
