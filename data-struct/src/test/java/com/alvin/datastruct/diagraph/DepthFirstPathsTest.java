package com.alvin.datastruct.diagraph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.Test;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/31
 * @since 1.0
 **/
public class DepthFirstPathsTest {

    @Test
    public void test() {
        //城市数量
        int totalNumber =  20;
        Graph G = new Graph(totalNumber);
        //添加城市的交通路线
        G.addEdge(0,1);
        G.addEdge(6,9);
        G.addEdge(1,8);
        G.addEdge(1,12);
        G.addEdge(8,12);
        G.addEdge(6,10);
        G.addEdge(4,8);

        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(G, 0);
        Stack<Integer> path = depthFirstPaths.pathTo(12);
        StringBuilder sb = new StringBuilder();
        //遍历栈对象
        for (Integer v : path) {
            sb.append(v+"->");
        }

        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb);
    }
}