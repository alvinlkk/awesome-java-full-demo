/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.fanxin;

import java.io.Serializable;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/9/4
 * @since 1.0
 **/
public class Interval<T extends Comparable & Serializable> implements Serializable {

    private T lower;

    private T upper;

    public Interval(T first, T second) {

    }

}
