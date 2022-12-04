/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.juc;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/23
 * @since 1.0
 **/
@Slf4j(topic = "a.ScheduledThreadPoolExecutorTest")
public class ScheduledThreadPoolExecutorTest {

    @Test
    public void testScheduleWithFixedDelay() throws InterruptedException {
        // 创建调度任务线程池
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        // 按照上次执行完成后固定延迟时间调度
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                log.info("scheduleWithFixedDelay ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 2, TimeUnit.SECONDS);

        Thread.sleep(10000);
    }

    @Test
    public void testScheduleAtFixedRate() throws InterruptedException {
        // 创建调度任务线程池
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        // 按照固定2秒时间执行
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                log.info("scheduleWithFixedDelay ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 2, TimeUnit.SECONDS);

        Thread.sleep(10000);
    }


    @Test
    public void testResp() throws InterruptedException {
        // 创建调度任务线程池
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        // 按照上次执行完成后固定延迟5秒时间调度
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            log.info("exec schedule ...");
        }, 1, 5, TimeUnit.SECONDS);

        while (true) {
            // 获取剩余时间
            long delay = scheduledFuture.getDelay(TimeUnit.SECONDS);
            log.info("下次执行剩余时间{}秒", delay);
           Thread.sleep(1000);
        }
    }

    // 通过ScheduledThreadPoolExecutor实现每周四 18:00:00 定时执行任务
    @Test
    public void test() {
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        // 获取周四时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        // 如果 当前时间 > 本周周四，必须找到下周周四
        if(now.compareTo(time) > 0) {
            time = time.plusWeeks(1);
        }
        System.out.println(time);
        // initailDelay 代表当前时间和周四的时间差
        // period 一周的间隔时间
        long initailDelay = Duration.between(now, time).toMillis();
        long period = 1000 * 60 * 60 * 24 * 7;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {
            System.out.println("running...");
        }, initailDelay, period, TimeUnit.MILLISECONDS);
    }
}
