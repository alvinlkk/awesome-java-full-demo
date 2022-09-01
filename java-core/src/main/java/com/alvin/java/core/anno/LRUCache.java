package com.alvin.java.core.anno;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (cxw@bsfit.com.cn)
 * @date: 2022/8/31  17:23
 * @version: 1.0.0
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    /**
     * 缓存允许的最大容量
     */
    private final int maxSize;

    public LRUCache(int initialCapacity, int maxSize) {
        // accessOrder必须为true
        super(initialCapacity, 0.75f, true);
        this.maxSize = maxSize;
    }

    // 重写
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当键值对个数超过最大容量时，返回true，触发删除操作
        return size() > maxSize;
    }

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(5, 5);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");
        // 做一次查询
        cache.get("1");
        cache.put("5", "5");
        cache.put("6", "6");
        cache.put("7", "7");
        System.out.println(cache);
    }

}
