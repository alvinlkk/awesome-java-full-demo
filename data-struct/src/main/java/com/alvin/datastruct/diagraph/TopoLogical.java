/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct.diagraph;

import java.util.Stack;

/**
 * 拓扑排序
 *
 * @author alvin
 * @date 2022/11/2
 * @since 1.0
 **/
public class TopoLogical {
    //顶点的拓扑排序
    private Stack<Integer> order;

    //构造拓扑排序对象
    public TopoLogical(Digraph G) {
        //创建一个检测有向环的对象
        DirectedCycle cycle = new DirectedCycle(G);
        //判断G图中有没有环，如果没有环，则进行顶点排序：创建一个顶点排序对象
        if (!cycle.hasCycle()){
            DepthFirstOrder depthFirstOrder = new DepthFirstOrder(G);
            order = depthFirstOrder.reversePost();
        }
    }

    //判断图G是否有环
    private boolean isCycle(){
        return order==null;
    }

    //获取拓扑排序的所有顶点
    public Stack<Integer> order(){
        return order;
    }
}
