/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct.diagraph;

import java.util.Stack;

import org.junit.Test;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/11/2
 * @since 1.0
 **/
public class TopoLogicalTest {

    @Test
    public void test() {
        //准备有向图
        Digraph digraph = new Digraph(6);
        digraph.addEdge(0,2);
        digraph.addEdge(0,3);
        digraph.addEdge(2,4);
        digraph.addEdge(3,4);
        digraph.addEdge(4,5);
        digraph.addEdge(1,3);

        //通过TopoLogical对象堆有向图中的顶点进行排序
        TopoLogical topoLogical = new TopoLogical(digraph);

        //获取顶点的线性序列进行打印
        Stack<Integer> order = topoLogical.order();
        StringBuilder sb = new StringBuilder();
        while (order.size() != 0) {
            sb.append(order.pop()+"->");
        };
        String str = sb.toString();
        int index = str.lastIndexOf("->");
        str = str.substring(0,index);
        System.out.println(str);
    }
}
