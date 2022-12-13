package com.alvin.java.core.juc;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/5  20:34
 * @version: 1.0.0
 */
@Slf4j(topic = "c.AtomicMarkableReferenceTest")
public class AtomicMarkableReferenceTest {
    // 构造 AtomicMarkableReference, 初始标记为false
    static AtomicMarkableReference<String> ref = new AtomicMarkableReference<>("A", false);

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");
        other();
        Thread.sleep(1000);
        // 看看是否发生了变化
        log.debug("change {}", ref.isMarked());
    }
    private static void other() throws InterruptedException {
        new Thread(() -> {
            log.debug("change A->B {}", ref.compareAndSet(ref.getReference(), "B",
                    false, true));
        }, "t1").start();

        Thread.sleep(500);

        new Thread(() -> {
            log.debug("change B->A {}", ref.compareAndSet(ref.getReference(), "A",
                    true, true));
        }, "t2").start();
    }
}
