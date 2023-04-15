/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.juc;

import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/12/2
 * @since 1.0
 **/
@Slf4j(topic = "a.VolatileTest")
public class VolatileTest {

    static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) {
                // do other things
            }

            // ?????? 这行会打印吗？
            log.info("done .....");
        });
        t.start();

        Thread.sleep(1000);

        // 设置run = false
        run = false;
    }
}