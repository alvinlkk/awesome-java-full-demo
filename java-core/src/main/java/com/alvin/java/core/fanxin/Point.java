/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.fanxin;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/9/4
 * @since 1.0
 **/
public class Point<T extends Number, U extends Number> {

    private T pointX;

    private U pintY;

    public Point(T pointX, U pintY) {
        pointX.intValue();
        this.pointX = pointX;
        this.pintY = pintY;
    }

    public void showPoint() {
        System.out.println(pointX);
        System.out.println(pintY);
    }

    public <C> C calcResult(C c) {
        return null;
    }
}
