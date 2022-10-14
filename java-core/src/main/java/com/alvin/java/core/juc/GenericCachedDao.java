package com.alvin.java.core.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/10/14  16:53
 * @version: 1.0.0
 */
public class GenericCachedDao {

    // 缓存对象，这里用jvm缓存
    Map<String, String> cache = new HashMap<>();
    // 读写锁
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 读取操作
    public String getData(String key) {
        // 加读锁，防止其他线程修改缓存
        readWriteLock.readLock().lock();
        try {
            String value = cache.get(key);
            // 如果缓存命中，返回
            if(value != null) {
                return value;
            }
        } finally {
            // 释放读锁
            readWriteLock.readLock().unlock();
        }

        //如果缓存没有命中，从数据库中加载
        readWriteLock.writeLock().lock();
        try {
            // 细节，为防止重复查询数据库, 再次验证
            // 因为get 方法上面部分是可能多个线程进来的, 可能已经向缓存填充了数据
            String value = cache.get(key);
            if(value == null) {
                // 这里可以改成从数据库查询
                value = "alvin";
                cache.put(key, value);
            }
            return value;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 更新数据
    public void updateData(String key, String value) {
        // 加写锁
        readWriteLock.writeLock().lock();
        try {
            // 更新操作TODO

            // 清空缓存
            cache.remove(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
