/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.juc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/12/4
 * @since 1.0
 **/
@Slf4j(topic = "a.AccountAtomic")
public class AccountAtomic {
    // 余额
    private final AtomicInteger balance;

    public AccountAtomic(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    public int getBalance() {
        return balance.get();
    }

    public void withdraw(Integer amount) {
        balance.addAndGet(-1 * amount);
    }

    public static void main(String[] args) {
        // 账户10000元
        AccountAtomic account = new AccountAtomic(10000);
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
