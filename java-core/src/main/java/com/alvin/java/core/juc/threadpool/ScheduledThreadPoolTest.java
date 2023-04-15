package com.alvin.java.core.juc.threadpool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.alvin.java.core.juc.threadpool.custom.Executor;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/20  12:08
 * @version: 1.0.0
 */
@Slf4j(topic = "a.ScheduledThreadPoolTest")
public class ScheduledThreadPoolTest {

    @Test
    public void testException() throws InterruptedException {
        // 创建1个线程的调度任务线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 创建一个任务
        Runnable runnable = new Runnable() {

            volatile int num = 0;

            @Override
            public void run() {
                try {
                    num ++;
                    // 模拟执行报错
                    if(num > 5) {
                        throw new RuntimeException("执行错误");
                    }
                    log.info("exec num: [{}].....", num);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // 每隔1秒钟执行一次任务
        scheduledExecutorService.scheduleAtFixedRate(new RunnableWrapper(runnable, "测试调度任务"), 0, 1, TimeUnit.SECONDS);
        Thread.sleep(10000);
    }
}
