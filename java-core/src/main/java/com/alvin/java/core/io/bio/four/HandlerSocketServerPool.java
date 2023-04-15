package com.alvin.java.core.io.bio.four;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/3/19  15:34
 */
public class HandlerSocketServerPool {
    private ExecutorService executorService;

    public HandlerSocketServerPool(int maxThradNum, int queueSize) {
        executorService = new ThreadPoolExecutor(2, maxThradNum, 120, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize));
    }

    public void execute(Runnable target) {
        executorService.execute(target);
    }

}
