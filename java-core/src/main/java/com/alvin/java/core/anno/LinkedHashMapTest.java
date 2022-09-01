package com.alvin.java.core.anno;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (cxw@bsfit.com.cn)
 * @date: 2022/8/31  10:12
 * @version: 1.0.0
 */
public class LinkedHashMapTest {

    @Test
    public void test1() {
        // 创建默认的 LinkedHashMap
        Map<String, String> map = new LinkedHashMap<>();
        // 插入
        map.put("1", "1");
        map.put("2", "2");
        map.put("5", "5");
        map.put("4", "4");
        System.out.println(map);

        // 访问
        map.get("2");
        map.get("1");

        System.out.println(map);
    }

    @Test
    public void test2() {
        // 创建 LinkedHashMap, accessOrder设置为true
        Map<String, String> map = new LinkedHashMap<String, String>(16, 0.75f, true);
        // 插入
        map.put("1", "1");
        map.put("2", "2");
        map.put("5", "5");
        map.put("4", "4");
        System.out.println(map);

        // 访问
        map.get("2");
        map.get("1");

        System.out.println(map);
    }
}
