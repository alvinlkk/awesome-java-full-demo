package com.alvin.java.core.juc.heima.ch08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/11/30  13:35
 * @version: 1.0.0
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            System.out.println("task1 task2 finish...");
        });

        // 循环重用
        for (int i = 0; i < 3; i++) {
            service.submit(() -> {
                System.out.println("task1 begin...");
                try {
                    Thread.sleep(1000);
                    barrier.await();    // 2 - 1 = 1
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            service.submit(() -> {
                System.out.println("task2 begin...");
                try {
                    Thread.sleep(2000);
                    barrier.await();    // 1 - 1 = 0
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();

    }
}
