/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct.diagraph;

/**
 * 图的深度优先搜索算法
 *
 * @author alvin
 * @date 2022/10/31
 * @since 1.0
 **/
public class DepthFirstSearch {
    //索引代表顶点，值表示当前顶点是否已经被搜索
    private boolean[] marked;
    //记录有多少个顶点与s顶点相通
    private int count;

    //构造深度优先搜索对象，使用深度优先搜索找出G图中s顶点的所有相邻顶点
    public DepthFirstSearch(Graph G, int s) {
        //创建一个和图的顶点数一样大小的布尔数组
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    //使用深度优先搜索找出G图中v顶点的所有相邻顶点
    private void dfs(Graph G, int v) {
        //把当前顶点标记为已搜索
        marked[v] = true;
        //遍历v顶点的邻接表，得到每一个顶点w
        for (Integer w : G.adj(v)) {
            //遍历v顶点的邻接表，得到每一个顶点w
            if (!marked[w]) {
                //如果当前顶点w没有被搜索过，则递归搜索与w顶点相通的其他顶点
                dfs(G, w);
            }
        }

        //相通的顶点数量+1
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
