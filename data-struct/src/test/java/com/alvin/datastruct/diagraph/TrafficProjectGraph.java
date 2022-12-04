/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct.diagraph;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/31
 * @since 1.0
 **/
public class TrafficProjectGraph {

    public static void main(String[] args) throws Exception{
        //城市数量
        int totalNumber =  20;
        Graph G = new Graph(totalNumber);
        //添加城市的交通路线
        G.addEdge(0,1);
        G.addEdge(6,9);
        G.addEdge(3,8);
        G.addEdge(5,11);
        G.addEdge(2,12);
        G.addEdge(6,10);
        G.addEdge(4,8);

        //构建一个深度优先搜索对象，起点设置为顶点9
        DepthFirstSearch search = new DepthFirstSearch(G, 9);

        //调用marked方法，判断8顶点和10顶点是否与起点9相通
        System.out.println("顶点8和顶点9是否相通："+search.marked(8));
        System.out.println("顶点10和顶点9是否相通："+search.marked(10));

    }
}
