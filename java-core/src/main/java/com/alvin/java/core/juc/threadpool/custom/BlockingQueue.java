package com.alvin.java.core.juc.threadpool.custom;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>自定义任务队列, 用来存放任务 </p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/18  10:15
 * @version: 1.0.0
 */
@Slf4j(topic = "c.BlockingQueue")
public class BlockingQueue<T> {
    // 容量
    private final int capcity;
    // 双端任务队列容器
    private final Deque<T> deque = new ArrayDeque<>();
    // 重入锁
    private final ReentrantLock lock = new ReentrantLock();
    // 生产者条件变量
    private final Condition fullWaitSet = lock.newCondition();
    // 生产者条件变量
    private final Condition emptyWaitSet = lock.newCondition();

    public BlockingQueue(int capcity) {
        this.capcity = capcity;
    }

    // 阻塞的方式添加任务
    public void put(T task) {
        lock.lock();
        try {
            // 通过while的方式
            while (deque.size() >= capcity) {
                log.debug("wait to add queue");
                try {
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            deque.offer(task);
            log.debug("task add successfully");
            emptyWaitSet.signal();
        }  finally {
            lock.unlock();
        }
    }

    // 阻塞获取任务
    public T take() {
        lock.lock();
        try {
            // 通过while的方式
            while (deque.isEmpty()) {
                try {
                    log.debug("wait to take task");
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            fullWaitSet.signal();
            T task = deque.poll();
            log.debug("take task successfully");
            // 从队列中获取元素
            return task;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尝试添加任务
     * @param rejectPolicy
     * @param task
     */
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try{
            // 如果队列超过容量
            if (deque.size()> capcity){
                log.debug("task too much, do reject");
                rejectPolicy.reject(this, task);
            }else {
                deque.offer(task);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
