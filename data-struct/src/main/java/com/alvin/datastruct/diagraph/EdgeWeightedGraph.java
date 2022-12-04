/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct.diagraph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 加权无向图的实现
 *
 * @author alvin
 * @date 2022/11/3
 * @since 1.0
 **/
public class EdgeWeightedGraph {
    //顶点总数
    private final int V;
    //边的总数
    private int E;
    //邻接表
    private Queue<Edge>[] adj;

    //创建一个含有V个顶点的空加权无向图
    public EdgeWeightedGraph(int V) {
        //初始化顶点数量
        this.V = V;
        //初始化边的数量
        this.E = 0;
        //初始化邻接表
        this.adj = new Queue[V];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayDeque<>();
        }

    }

    //获取图中顶点的数量
    public int V() {
        return V;
    }

    //获取图中边的数量
    public int E() {
        return E;
    }


    //向加权无向图中添加一条边e
    public void addEdge(Edge e) {
        //需要让边e同时出现在e这个边的两个顶点的邻接表中
        int v = e.either();
        int w = e.other(v);

        adj[v].add(e);
        adj[w].add(e);

        //边的数量+1
        E++;
    }

    //获取和顶点v关联的所有边
    public Queue<Edge> adj(int v) {
        return adj[v];
    }

    //获取加权无向图的所有边
    public Queue<Edge> edges() {
        //创建一个队列对象，存储所有的边
        Queue<Edge> allEdges = new ArrayDeque<>();

        //遍历图中的每一个顶点，找到该顶点的邻接表，邻接表中存储了该顶点关联的每一条边
        //因为这是无向图，所以同一条边同时出现在了它关联的两个顶点的邻接表中，需要让一条边只记录一次；
        for (int v = 0; v < V; v++) {
            //遍历v顶点的邻接表，找到每一条和v关联的边
            for (Edge e : adj(v)) {
                if (e.other(v) < v) {
                    allEdges.add(e);
                }
            }
        }
        return allEdges;
    }
}
