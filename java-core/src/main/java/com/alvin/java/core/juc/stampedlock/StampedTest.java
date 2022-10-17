package com.alvin.java.core.juc.stampedlock;

import org.junit.Test;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/17  14:47
 * @version: 1.0.0
 */
public class StampedTest {

    @Test
    public void testStamped() throws InterruptedException {
        Point point = new Point();
        point.setX(1);
        point.setY(2);
        // 线程0 执行了乐观读
        Thread thread0 = new Thread(() -> {
            try {
                // 乐观读
                point.distanceFromOrigin();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread-0");
        thread0.start();

        Thread.sleep(500);
        // 线程1 执行写锁
        Thread thread1 = new Thread(() -> {
            // 乐观读
            try {
                point.move(3, 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread-1");
        thread1.start();

        thread0.join();
        thread1.join();
    }
}
