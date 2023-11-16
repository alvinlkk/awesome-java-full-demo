/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.juc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/12/4
 * @since 1.0
 **/
@Slf4j(topic = "a.AccountCAS")
public class AccountCAS {
    // 余额
    private final int balance;
    // Unsafe对象
    static final Unsafe unsafe;
    // balance 字段的偏移量
    static final long BALANCE_OFFSET;
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            // balance 属性在 AccountCAS 对象中的偏移量，用于 Unsafe 直接访问该属性
            BALANCE_OFFSET = unsafe.objectFieldOffset(AccountCAS.class.getDeclaredField("balance"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public AccountCAS(Integer balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(Integer amount) {
        // 自旋
        while (true) {
            // 获取老的余额
            int oldBalance = balance;
            // 获取新的余额
            int newBalance = oldBalance - amount;
            // 更新余额，BALANCE_OFFSET表示balance属性的偏移量， 返回true表示更新成功， false更新失败，继续更新
            if(unsafe.compareAndSwapInt(this, BALANCE_OFFSET, oldBalance, newBalance)) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        // 账户10000元
        AccountCAS account = new AccountCAS(10000);
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        // 1000个线程，每次取10元
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        // 打印账户余额和花费时间
        log.info("账户余额：{}, 花费时间: {}", account.getBalance(), (end-start)/1000_000 + " ms");
    }
}
