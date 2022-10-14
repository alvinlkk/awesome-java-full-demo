package com.alvin.java.core.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p>独占锁：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/10  16:21
 * @version: 1.0.0
 */
public class ExclusiveLock implements Lock {

    // 同步器，继承自AQS
    private static class Sync extends AbstractQueuedSynchronizer {

        // 重写获取锁的方式
        @Override
        protected boolean tryAcquire(int acquires) {
            assert acquires == 1;
            // cas的方式抢锁
            if(compareAndSetState(0, 1)) {
                // 设置抢占锁的线程为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1;

            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            };
            //设置抢占锁的线程为null
            setExclusiveOwnerThread(null);
            // 释放锁
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        ExclusiveLock exclusiveLock = new ExclusiveLock();


        new Thread(() -> {
            try {
                exclusiveLock.lock();
                System.out.println("thread1 get lock");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                exclusiveLock.unlock();
                System.out.println("thread1 release lock");
            }

        }).start();
        Thread.sleep(10);

        Thread t2 = new Thread(() -> {
            try {
                exclusiveLock.lock();
                System.out.println("thread2 get lock");
                //Thread.sleep(5000);
            }  finally {
                exclusiveLock.unlock();
                System.out.println("thread2 release lock");
            }

        });
        t2.setName("thread2");
        t2.start();

        t2.interrupt();
        Thread.currentThread().join();

    }
}
