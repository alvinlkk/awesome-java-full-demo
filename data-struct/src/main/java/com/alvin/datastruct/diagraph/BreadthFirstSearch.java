/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct.diagraph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 图的广度优先搜索算法
 *
 * @author alvin
 * @date 2022/10/31
 * @since 1.0
 **/
public class BreadthFirstSearch {
    //索引代表顶点，值表示当前顶点是否已经被搜索
    private final boolean[] marked;
    //记录有多少个顶点与s顶点相通
    private int count;
    //用来存储待搜索邻接表的点
    private final Queue<Integer> waitSearch;

    //构造广度优先搜索对象，使用广度优先搜索找出G图中s顶点的所有相邻顶点
    public BreadthFirstSearch(Graph G, int s) {
        this.marked = new boolean[G.V()];
        this.count = 0;
        this.waitSearch = new ArrayDeque<>();

        bfs(G, s);
    }

    //使用广度优先搜索找出G图中v顶点的所有相邻顶点
    private void bfs(Graph G, int v) {
        //把当前顶点v标识为已搜索
        marked[v] = true;
        //让顶点v进入队列，待搜索
        waitSearch.add(v);
        //通过循环，如果队列不为空，则从队列中弹出一个待搜索的顶点进行搜索
        while (!waitSearch.isEmpty()) {
            //弹出一个待搜索的顶点
            Integer wait = waitSearch.poll();
            //遍历wait顶点的邻接表
            for (Integer w : G.adj(wait)) {
                if (!marked[w]) {
                    bfs(G, w);
                }
            }
        }
        //让相通的顶点+1；
        count++;

    }

    //判断w顶点与s顶点是否相通
    public boolean marked(int w) {
        return marked[w];
    }

    //获取与顶点s相通的所有顶点的总数
    public int count() {
        return count;
    }
}
