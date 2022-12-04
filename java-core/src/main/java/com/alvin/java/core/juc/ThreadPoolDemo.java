/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/22
 * @since 1.0
 **/
@Slf4j(topic = "a.ThreadPoolDemo")
public class ThreadPoolDemo {

    @Test
    public void testSubmit() throws ExecutionException, InterruptedException {
        // 创建一个核心线程数为5的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));

        // 创建一个计算任务
        Callable<Integer> myTask = new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 0; i < 10000; i++) {
                    result += i;
                }
                Thread.sleep(1000);
                return result;
            }
        };

        log.info("start submit task .....");
        Future<Integer> future = threadPoolExecutor.submit(myTask);

        Integer sum = future.get();
        log.info("get submit result: [{}]", sum);

        // use sum do other things
    }
}
