/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.error.spi;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/24
 * @since 1.0
 **/
public class SPITest {

    @Test
    public void test() {
        // 调用SpringFactoriesLoader.loadFactories方法加载Phone接口所有实现类的实例
        List<Phone> spis = SpringFactoriesLoader.loadFactories(Phone.class,
                Thread.currentThread().getContextClassLoader());

        // 遍历Phone接口实现类实例
        for (Phone spi : spis) {
            System.out.println(spi.getSystemInfo());;
        }
    }
}
