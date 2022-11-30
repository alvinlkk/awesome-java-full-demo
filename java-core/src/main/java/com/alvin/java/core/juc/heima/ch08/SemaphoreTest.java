package com.alvin.java.core.juc.heima.ch08;

import static java.lang.Thread.sleep;

import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/11/29  19:39
 * @version: 1.0.0
 */
@Slf4j(topic = "c.SemaphoreTest")
public class SemaphoreTest {

    public static void main(String[] args) {
        // 1. 创建 semaphore 对象
        Semaphore semaphore = new Semaphore(2);
        // 2. 10个线程同时运行
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                // 3. 获取许可
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    sleep(1);
                    log.debug("end...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 4. 释放许可
                    semaphore.release();
                }
            }).start();
        }
    }
}
