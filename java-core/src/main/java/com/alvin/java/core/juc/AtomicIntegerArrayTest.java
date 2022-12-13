package com.alvin.java.core.juc;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/5  20:50
 * @version: 1.0.0
 */
public class AtomicIntegerArrayTest {
    public static void main(String[] args) throws Exception{
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        Thread t1 = new Thread(()->{
            int index;
            for(int i=1; i<100000; i++) {
                index = i%10; //范围0~9
                array.incrementAndGet(index);
            }
        });

        Thread t2 = new Thread(()->{
            int index;
            for(int i=1; i<100000; i++) {
                index = i%10; //范围0~9
                array.decrementAndGet(index);
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(5 * 1000);
        System.out.println(array.toString());
    }
}
