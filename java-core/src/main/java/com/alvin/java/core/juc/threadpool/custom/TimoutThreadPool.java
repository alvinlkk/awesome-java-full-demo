package com.alvin.java.core.juc.threadpool.custom;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>线程池类：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/18  12:16
 * @version: 1.0.0
 */
@Slf4j(topic = "c.TimoutThreadPool")
public class TimoutThreadPool implements Executor {

    /**
     * 任务队列
     */
    private final TimeoutBlockingQueue<Runnable> taskQueue;

    /**
     * 核心工作线程数
     */
    private final int coreSize;

    /**
     * 工作线程集合
     */
    private final Set<Worker> workers = new HashSet<>();

    private final long timeOut;

    private final TimeUnit timeUnit;

    public TimoutThreadPool(int coreSize, int capcity,long timeOut, TimeUnit timeUnit) {
        this.coreSize = coreSize;
        this.taskQueue = new TimeoutBlockingQueue<>(capcity);
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
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
                taskQueue.offer(task, timeOut, timeUnit);
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
            while (task != null || (task = taskQueue.poll(timeOut,timeUnit)) != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 执行完后，设置任务为空
                    task = null;
                }
            }

            // yichu
            synchronized (workers){
                log.debug("remove worker successfully");
                workers.remove(this);
            }
        }
    }
}
