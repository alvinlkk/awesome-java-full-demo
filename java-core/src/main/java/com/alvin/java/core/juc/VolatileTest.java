package com.alvin.java.core.juc;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/2  14:46
 * @version: 1.0.0
 */
@Slf4j(topic = "c.VolatileTest")
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

        run = false;
    }
}
