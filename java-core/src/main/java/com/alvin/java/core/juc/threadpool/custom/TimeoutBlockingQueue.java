package com.alvin.java.core.juc.threadpool.custom;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
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
public class TimeoutBlockingQueue<T> {
    // 容量
    private int capcity;
    // 双端任务队列容器
    private Deque<T> deque = new ArrayDeque<>();
    // 重入锁
    private ReentrantLock lock = new ReentrantLock();
    // 生产者条件变量
    private Condition fullWaitSet = lock.newCondition();
    // 生产者条件变量
    private Condition emptyWaitSet = lock.newCondition();

    public TimeoutBlockingQueue(int capcity) {
        this.capcity = capcity;
    }

    // 带超时时间的获取
    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try{
            // 将 timeout 统一转换为 纳秒
            long nanos = unit.toNanos(timeout);
            while (deque.isEmpty()){
                try {
                    if (nanos<=0){
                        return null;
                    }
                    // 返回的是剩余的等待时间，更改navos的值，使虚假唤醒的时候可以继续等待
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            fullWaitSet.signal();
            return deque.getFirst();
        }finally {
            lock.unlock();
        }
    }

    // 带超时时间的增加
    public boolean offer(T task , long timeout , TimeUnit unit){
        lock.lock();
        try{
            // 将 timeout 统一转换为 纳秒
            long nanos = unit.toNanos(timeout);
            while (deque.size() == capcity){
                try {
                    if (nanos<=0){
                        return false;
                    }
                    // 更新剩余需要等待的时间
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {}", task);
            deque.addLast(task);
            emptyWaitSet.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }
}
