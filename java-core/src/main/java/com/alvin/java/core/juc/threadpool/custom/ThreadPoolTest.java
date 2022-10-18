package com.alvin.java.core.juc.threadpool.custom;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/18  12:31
 * @version: 1.0.0
 */
@Slf4j(topic = "c.threadpooltest")
public class ThreadPoolTest {

    @Test
    public void testThreadPool1() throws InterruptedException {
        Executor executor = new ThreadPool(2, 4, new RejectPolicy() {
            @Override
            public void reject(BlockingQueue queue, Object task) {
                log.error("task too much");
            }
        });
        // 提交任务
        for (int i = 0; i < 6; i++) {
            final  int j = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(10);
                    log.info("run task {}", j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(10);
        }

        Thread.sleep(10000);
    }

}
