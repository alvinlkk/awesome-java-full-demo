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
public class Pair<T> {

    private T first;

    private T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public static void main(String[] args) {
        Pair<? extends Integer> pair = new Pair<>(12, 12);
    }
}
