/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.junit.Test;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/24
 * @since 1.0
 **/
public class SPITest {

    @Test
    public void test1() {
        // 调用ServiceLoader的load方法
        ServiceLoader<Phone> phoneServiceLoader = ServiceLoader.load(Phone.class);

        Iterator<Phone> iterator = phoneServiceLoader.iterator();
        while (iterator.hasNext()) {
            Phone phone = iterator.next();
            if(phone != null) {
                String systemInfo = phone.getSystemInfo();
                System.out.println(systemInfo);
            }
        }
    }
}
