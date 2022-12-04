/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.juc;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/12/4
 * @since 1.0
 **/
@Slf4j(topic = "a.AccountUnsafe")
public class AccountUnsafe {
    // 余额
    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return balance;
    }

    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }

    public static void main(String[] args) {
        // 账户10000元
        AccountUnsafe account = new AccountUnsafe(10000);
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
