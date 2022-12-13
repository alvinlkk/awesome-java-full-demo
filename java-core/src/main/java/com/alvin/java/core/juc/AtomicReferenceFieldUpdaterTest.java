package com.alvin.java.core.juc;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import lombok.Data;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/5  21:00
 * @version: 1.0.0
 */
@Data
public class AtomicReferenceFieldUpdaterTest {

    private volatile int age = 10;

    private int age2;

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AtomicReferenceFieldUpdaterTest.class, "age");

        AtomicReferenceFieldUpdaterTest ref = new AtomicReferenceFieldUpdaterTest();
        // 对volatile 的age字段+1
        integerFieldUpdater.getAndIncrement(ref);
        System.out.println(ref.getAge());

        // 修改 非volatile的age2
        integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AtomicReferenceFieldUpdaterTest.class, "age2");
        integerFieldUpdater.getAndIncrement(ref);
    }
}

