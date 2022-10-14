package com.alvin.java.core.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/12  18:47
 * @version: 1.0.0
 */
public class ReentrantLockDemo {

    @Test
    public void testRepeatLock() {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " first get lock");
            // 再次获取锁
            tryAgainLock(reentrantLock);
        }finally {
            reentrantLock.unlock();
        }
    }

    public void tryAgainLock(ReentrantLock reentrantLock) {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " second get lock");
        }finally {
            reentrantLock.unlock();
        }
    }

    @Test
    public void testInterrupt() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        // 主线程普通加锁
        System.out.println("主线程优先获取锁");
        lock.lock();
        try {
            // 创建子线程
            Thread t1 = new Thread(() -> {
                try {
                    System.out.println("t1尝试获取打断锁");
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    System.out.println("t1没有获取到锁，被打断，直接返回");
                    return;
                }
                try {
                    System.out.println("t1成功获取锁");
                } finally {
                    System.out.println("t1释放锁");
                    lock.unlock();
                }
            }, "t1");
            t1.start();
            Thread.sleep(2000);
            System.out.println("主线程进行打断锁");
            t1.interrupt();
        } finally {
            // 主线程解锁
            System.out.println("主线程优先释放锁");
            lock.unlock();
        }
    }

    @Test
    public void testLockTimeout() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    System.out.println("t1获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                System.out.println("t1被打断，获取不到锁");
                return;
            }
            try {
                System.out.println("t1获取到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");
        // 主线程加锁
        lock.lock();
        System.out.println("主线程获取到锁");

        t1.start();
        Thread.sleep(3000);
        try {
            System.out.println("主线程释放了锁");
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testCondition() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        //创建新的条件变量
        Condition condition = lock.newCondition();
        Thread thread0 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程0获取锁");
                // sleep不会释放锁
                Thread.sleep(500);
                //进入休息室等待
                System.out.println("线程0释放锁，进入等待");
                condition.await();
                System.out.println("线程0被唤醒了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread0.start();
        //叫醒
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程1获取锁");
                //唤醒
                condition.signal();
                System.out.println("线程1唤醒线程0");
            } finally {
                lock.unlock();
                System.out.println("线程1释放锁");
            }
        });
        thread1.start();

        thread0.join();
        thread1.join();
    }
}
