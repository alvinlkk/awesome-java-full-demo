/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.juc;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/27
 * @since 1.0
 **/
@Slf4j(topic = "a.CountDownLatchTest")
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个倒时器，默认10个数量
        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService service = Executors.newFixedThreadPool(10);
        // 设置进度数据
        String[] personProcess = new String[10];
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int finalJ = i;
            service.submit(() -> {
                // 模拟10个人的进度条
                for (int j = 0; j <= 100; j++) {
                    // 模拟网速快慢，随机生成
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 设置进度数据
                    personProcess[finalJ] = j + "%";
                   log.info("{}", Arrays.toString(personProcess));
                }

                // 运行结束，倒时器 - 1
                latch.countDown();
            });
        }
        // 打开"阀门"
        latch.await();
       log.info("王者峡谷到了");
        service.shutdown();
    }
}
