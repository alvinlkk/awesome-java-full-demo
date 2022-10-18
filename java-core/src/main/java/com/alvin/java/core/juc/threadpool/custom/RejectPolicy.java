package com.alvin.java.core.juc.threadpool.custom;

/**
 * <p>拒绝策略的函数式接口：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/18  13:15
 * @version: 1.0.0
 */
@FunctionalInterface
public interface RejectPolicy<T> {

    /**
     * 拒绝策略的接口
     * @param queue
     * @param task
     */
    void reject(BlockingQueue<T> queue, T task);
}
