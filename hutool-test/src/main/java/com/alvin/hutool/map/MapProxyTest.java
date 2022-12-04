/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.hutool.map;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import cn.hutool.core.map.MapProxy;
import cn.hutool.core.map.MapUtil;
import lombok.Data;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/4
 * @since 1.0
 **/
public class MapProxyTest {

    @Test
    public void testMapProxy1() {
        Map<String, Object> userMap = MapUtil.newHashMap(16);
        userMap.put("username", "alvin");
        userMap.put("age", 20);

        // 使用map的时候, 需要进行强转，一旦类型错误，会报错
        String age = (String)userMap.get("age");
    }

    @Test
    public void testMapProxy2() {
        Map<String, Object> userMap = MapUtil.newHashMap(16);
        userMap.put("username", "alvin");
        userMap.put("age", 20);

        MapProxy mapProxy = MapProxy.create(userMap);
        Integer age = mapProxy.getInt("age", 18);
        Assert.assertTrue(age == 20);

        // 通过代理的方式
        MapUser mapUser = mapProxy.toProxyBean(MapUser.class);
        // 后续访问会变的更加安全
        Assert.assertTrue(mapUser.getAge() == 20);

        mapUser.setAge(30);
        Assert.assertTrue(mapUser.getAge() == 30);
    }

}


interface MapUser {
    String getUsername();

    Integer getAge();

    MapUser setAge(Integer age);
}
