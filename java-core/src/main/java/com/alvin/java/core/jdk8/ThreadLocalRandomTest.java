/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.jdk8;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/12/14
 * @since 1.0
 **/
public class ThreadLocalRandomTest {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        System.out.println(random.nextInt());
        System.out.println(random.nextInt(10));
    }
}
