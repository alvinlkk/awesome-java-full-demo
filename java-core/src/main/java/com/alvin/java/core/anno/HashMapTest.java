package com.alvin.java.core.anno;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/8/29  14:25
 * @version: 1.0.0
 */
public class HashMapTest {

    @Test
    public void test1() {
        Map<String, Integer> map = new HashMap();

        map.put("cxw", 1);
        map.put("yms", 2);
        // 会覆盖前面的key yms
        map.put("cxw", 5);
        System.out.println(map);  // {cxw=5, yms=2}

        // 不存在cxw 这个key,才会覆盖
        map.putIfAbsent("cxw", 8);
        map.putIfAbsent("cc", 1);
        System.out.println(map); // {cc=1, cxw=5, yms=2}

        Integer cxwInt = map.get("cxw");
        System.out.println(cxwInt);  // 5
        // 不存在key，使用默认值
        Integer defaultGet = map.getOrDefault("kk", 0);  // 0

        // 根据key删除
        map.remove("cc");

        // 根据key value删除, key， value都等值才会删除成功，所以这里删除不成功
        map.remove("cxw", 10);
        System.out.println(map);
    }

    @Test
    public void test2() {
        Map<String, Integer> map = new HashMap();
        map.put("cxw", 1);
        map.put("yms", 2);
        map.put("kk", 2);

        // 计算
        map.compute("cxw", new BiFunction<String, Integer, Integer>() {
            @Override
            public Integer apply(String key, Integer integer) {
                return 100;
            }
        });
        System.out.println(map);  //{kk=2, cxw=100, yms=2}

        map.compute("cxw", new BiFunction<String, Integer, Integer>() {
            @Override
            public Integer apply(String key, Integer integer) {
                // 返回null,会删除cxw这个key
                return null;
            }
        });
        System.out.println(map);  //{kk=2, yms=2}

        // 只有key不存在才会进行操作， yms存在,所以保持不变
        map.computeIfAbsent("yms", new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return 10;
            }
        });
        System.out.println(map);  //{kk=2, yms=2}

        // 只有key存在才会进行操作， yms存在,所以发生变化
        map.computeIfPresent("yms", new BiFunction<String, Integer, Integer>() {
            @Override
            public Integer apply(String s, Integer integer) {
                return 10;
            }
        });
        System.out.println(map);  //{kk=2, yms=10}
    }

    @Test
    public void test3() {
        Map<String, Integer> map = new HashMap();
        map.put("cxw", 1);
        map.put("yms", 2);
        map.put("kk", 2);
        // 根据key排序
        List<Map.Entry<String, Integer>> entrys = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
                Collectors.toList()
        );
        entrys.forEach(item -> {
            System.out.println(item.getKey());
        });
    }

    @Test
    public void test4() {
        Map<String, Integer> map = new HashMap(10);
        map.put("cxw", 1);
        map.put("yms", 2);
        map.put("kk", 2);

    }
}
