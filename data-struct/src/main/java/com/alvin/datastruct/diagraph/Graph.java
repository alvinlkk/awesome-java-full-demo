/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct.diagraph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 无向图的表示
 *
 * @author alvin
 * @date 2022/10/30
 * @since 1.0
 **/
public class Graph {
    //顶点数目
    private final int V;
    //边的数目
    private int E;
    //邻接表,队列的形式
    private Queue<Integer>[] adj;

    public Graph(int V) {
        // 初始化顶点数量
        this.V = V;
        //初始化边的数量
        this.E = 0;
        //初始化邻接表
        this.adj = new Queue[V];
        //初始化邻接表中的空队列
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayDeque<>();
        }
    }

    public void addEdge(int v, int w) {
        //把w添加到v的链表中，这样顶点v就多了一个相邻点w
        adj[v].add(w);
        //把v添加到w的链表中，这样顶点w就多了一个相邻点v
        adj[w].add(v);
        //边的数目自增1
        E++;
    }

    //获取顶点数目
    public int V() {
        return V;
    }

    //获取边的数目
    public int E(){
        return E;
    }

    //获取和顶点v相邻的所有顶点
    public Queue<Integer> adj(int v) {
        return adj[v];
    }
}
