/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.fanxin;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/9/5
 * @since 1.0
 **/
public class FxMethod {

    public static <T> T getMiddleNumber(T ... numbers) {
        return null;
    }

    public <T, U> void showNumber(T t, U u) {
        System.out.println("t = " + t);
        System.out.println("u = " + u);;
    }

    public static void main(String[] args) {
        getMiddleNumber(10, 11, 12).intValue();
    }
}
