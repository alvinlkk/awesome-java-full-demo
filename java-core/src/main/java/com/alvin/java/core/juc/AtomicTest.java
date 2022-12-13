package com.alvin.java.core.juc;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/5  20:07
 * @version: 1.0.0
 */
public class AtomicTest {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        // 1000个线程自增，正常结果为1000
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                // 自增，相当于i++
                atomicInteger.getAndIncrement();
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(atomicInteger.get());;
    }
}
