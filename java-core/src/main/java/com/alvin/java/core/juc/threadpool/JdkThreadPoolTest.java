package com.alvin.java.core.juc.threadpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.ListSelectionModel;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/19  20:21
 * @version: 1.0.0
 */
@Slf4j(topic = "a.JdkThreadPoolTest")
public class JdkThreadPoolTest {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 100,
                100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 20; i++) {
            // execute的方式提交任务
            threadPool.execute(() -> {
                log.info("execute ....");
            });
        }

        // submit runnable
        Future<String> futureCall = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "callable Result";
            }
        });
        // 阻塞等待结果返回
        String result = futureCall.get();
        log.info("submit callable: {}", result);

        // submit runnable
        Future<String> future = threadPool.submit(new Runnable() {
            @Override
            public void run() {
                log.info("submit runnable ....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "submit result");
        // 阻塞等待结果返回
        result = future.get();
        log.info("submit runnable: {}", result);

        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            callables.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(2000);
                    return "callable" + j;
                }
            });
        }
        List<Future<String>> futures = threadPool.invokeAll(callables);
        for (Future<String> stringFuture : futures) {
            String invoke = stringFuture.get();
            log.info("invoke result: {}", invoke);
        }

        String invokeAny = threadPool.invokeAny(callables);
        log.info("invoke any: {}", invokeAny);
    }

    @Test
    public void test3() {
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1)) {
            @Override protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("beforeExecute is called");
            }
            @Override protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("afterExecute is called");
            }
            @Override protected void terminated() {
                System.out.println("terminated is called");
            }
        };

        executor.submit(() -> System.out.println("this is a task"));
        executor.shutdown();
    }
}
