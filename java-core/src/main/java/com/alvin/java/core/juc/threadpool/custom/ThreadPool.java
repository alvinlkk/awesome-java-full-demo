package com.alvin.java.core.juc.threadpool.custom;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>线程池类：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/18  12:16
 * @version: 1.0.0
 */
@Slf4j(topic = "c.ThreadPool")
public class ThreadPool implements Executor {

    /**
     * 任务队列
     */
    private BlockingQueue<Runnable> taskQueue;

    /**
     * 核心工作线程数
     */
    private int coreSize;

    /**
     * 工作线程集合
     */
    private Set<Worker> workers = new HashSet<>();

    /**
     * 拒绝策略
     */
    private RejectPolicy rejectPolicy;

    /**
     *  创建线程池
     * @param coreSize 工作线程数量
     * @param capcity 阻塞队列容量
     */
    public ThreadPool(int coreSize, int capcity, RejectPolicy rejectPolicy) {
        this.coreSize = coreSize;
        this.taskQueue = new BlockingQueue<>(capcity);
        this.rejectPolicy = rejectPolicy;
    }

    /**
     * 提交任务执行
     */
    @Override
    public void execute(Runnable task) {
        synchronized (workers) {
            // 如果工作线程数小于阈值，直接开始任务执行
            if(workers.size() < coreSize) {
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
            } else {
                // 如果超过了阈值，加入到队列中
                //taskQueue.put(task);
                // 调用tryPuth的方式
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    /**
     * 工作线程，对执行的任务做了一层包装处理
     */
    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 如果任务不为空，或者可以从队列中获取任务
            while (task != null || (task = taskQueue.take()) != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 执行完后，设置任务为空
                    task = null;
                }
            }

            // 移除工作线程
            synchronized (workers){
                log.debug("remove worker successfully");
                workers.remove(this);
            }
        }
    }
}
