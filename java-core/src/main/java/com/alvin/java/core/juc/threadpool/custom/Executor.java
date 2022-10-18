package com.alvin.java.core.juc.threadpool.custom;

/**
 * <p>定义一个执行器的接口：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/18  12:31
 * @version: 1.0.0
 */
public interface Executor {

    /**
     * 提交任务执行
     * @param task 任务
     */
    void execute(Runnable task);
}
