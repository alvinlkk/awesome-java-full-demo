package com.alvin.java.core.juc.heima.ch08;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/11/30  13:35
 * @version: 1.0.0
 */
@Slf4j(topic = "c.CyclicBarrierPig")
public class CyclicBarrierPig {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.println("主人看看哪个猪跑最快，最肥...");
        });

        // 循环跑3次
        for (int i = 0; i < 3; i++) {
            // 5条猪开始跑
            for(int j = 0; j<5; j++) {
                int finalJ = j;
                service.submit(() -> {
                   log.info("pig{} is run .....", finalJ);
                    try {
                        // 随机时间，模拟跑花费的时间
                        Thread.sleep(new Random().nextInt(5000));
                        log.info("pig{} reach barrier .....", finalJ);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        service.shutdown();
    }
}
