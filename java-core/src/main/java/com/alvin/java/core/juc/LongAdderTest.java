package com.alvin.java.core.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/5  21:16
 * @version: 1.0.0
 */
public class LongAdderTest {

    public static void main(String[] args) {
        System.out.println("LongAdder ...........");
        for (int i = 0; i < 5; i++) {
            addFunc(() -> new LongAdder(), adder -> adder.increment());
        }
        System.out.println("AtomicLong ...........");
        for (int i = 0; i < 5; i++) {
            addFunc(() -> new AtomicLong(), adder -> adder.getAndIncrement());
        }
    }

    private static <T> void addFunc(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        long start = System.nanoTime();
        List<Thread> ts = new ArrayList<>();

        // 40个线程，每人累加 50 万
        for (int i = 0; i < 40; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(adder + " cost:" + (end - start)/1000_000);
    }
}
