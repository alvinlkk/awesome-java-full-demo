/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 有向图
 *
 * @author alvin
 * @date 2022/10/26
 * @since 1.0
 **/
@Data
@Slf4j(topic = "a.DirectedDiagraph")
public class DirectedDiagraph {

    /**
     * 有向图的的顶点信息
     */
    private Map<String, Vertex> vertextMap = new HashMap<>();

    /**
     * 边的数量
     */
    private int edgeNum;

    /**
     * 添加顶点信息
     *
     * @param vertexId   顶点的id
     * @param vertexName 顶点的名称
     */
    public void addVertex(String vertexId, String vertexName) {
        if (StrUtil.isEmpty(vertexId)) {
            throw new RuntimeException("顶点id不能为空");
        }

        Vertex node = new Vertex().setId(vertexId).setName(vertexName);
        // 添加到有向图中
        vertextMap.put(vertexId, node);
    }

    /**
     * 添加边信息
     *
     * @param fromVertexId   边的起始节点
     * @param targetVertexId 边的目标节点
     * @param edgeId         边id
     * @param edgeName       边名称
     */
    public void addEdge(String fromVertexId, String targetVertexId, String edgeId, String edgeName) {
        if (StrUtil.isEmpty(fromVertexId) || StrUtil.isEmpty(targetVertexId)) {
            throw new RuntimeException("边的起始顶点或者目标顶点不能为空");
        }
        Edge edge = new Edge().setTargetVertexId(targetVertexId).setId(edgeId).setName(edgeName);
        // 获取顶点
        Vertex fromVertex = vertextMap.get(fromVertexId);
        // 添加到边中
        fromVertex.getEdges().add(edge);
        // 边的数量+1
        edgeNum++;
    }

    /**
     * 添加边信息
     * @param fromVertexId   边的起始节点
     * @param targetVertexId 边的目标节点
     */
    public void addEdge(String fromVertexId, String targetVertexId) {
        this.addEdge(fromVertexId, targetVertexId, null, null);
    }

    /**
     * 获取图中边的数量
     */
    public int getEdgeNum() {
        return edgeNum;
    }

    /**
     * 获取两个顶点之间所有可能的数据
     *
     * @param fromVertexId 起始顶点
     * @param targetVertexId 目标顶点
     * @return
     */
    public Set<String> findAllPaths(String fromVertexId, String targetVertexId) {
        CalcTwoVertexPathlgorithm calcTwoVertexPathlgorithm = new CalcTwoVertexPathlgorithm(this, fromVertexId, targetVertexId);
        // 先计算
        calcTwoVertexPathlgorithm.calcPaths();
        // 打印找到的路径
        calcTwoVertexPathlgorithm.printAllPaths();
        // 然后返回所有的内容
        return calcTwoVertexPathlgorithm.getAllVertexs();
    }

    /**
     * 顶点
     */
    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    @NoArgsConstructor
    class Vertex {
        /**
         * 顶点id
         */
        private String id;

        /**
         * 顶点的名称
         */
        private String name;

        /**
         * 顶点发散出去的边信息
         */
        private List<Edge> edges = new ArrayList<>();

    }

    /**
     * 边
     */
    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    @NoArgsConstructor
    class Edge {

        /**
         * 边的目标id
         */
        private String targetVertexId;

        /**
         * 边的id
         */
        private String id;

        /**
         * 边的名称
         */
        private String name;
    }
}

/**
 * 计算两个顶点之间路径的算法
 */
@Slf4j(topic = "a.CalcTwoVertexPathlgorithm")
class CalcTwoVertexPathlgorithm {

    /**
     * 起始顶点
     */
    private final String fromVertexId;

    /**
     * 查询的目标顶点
     */
    private final String toVertextId;

    /**
     * 当前的图
     */
    private final DirectedDiagraph directedDiagraph;

    /**
     * 所有的路径
     */
    private final List<List<String>> allPathList = new ArrayList<>();

    public CalcTwoVertexPathlgorithm(DirectedDiagraph directedDiagraph, String fromVertexId, String toVertextId) {
        this.fromVertexId = fromVertexId;
        this.toVertextId = toVertextId;
        this.directedDiagraph = directedDiagraph;
    }

    /**
     * 打印所有的路径
     */
    public void printAllPaths() {
        log.info("the path betweent {} and {}:", fromVertexId, toVertextId);
        allPathList.forEach(item -> {
            log.info("{}", item);
        });
    }

    /**
     * 获取两点之间所有可能的顶点数据
     * @return
     */
    public Set<String> getAllVertexs() {
        return allPathList.stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public void calcPaths() {
        // 先清理之前调用留下的数据
        allPathList.clear();

        DirectedDiagraph.Vertex fromNode = directedDiagraph.getVertextMap().get(fromVertexId);
        DirectedDiagraph.Vertex toNode = directedDiagraph.getVertextMap().get(toVertextId);
        // 无法找到边
        if (fromNode == null || toNode == null) {
            throw new RuntimeException("顶点id不存在");
        }

        // 如果其实节点等于目标节点，则也作为一个边
        if (fromNode == toNode) {
            List<String> paths = new ArrayList<>();
            paths.add(fromVertexId);
            allPathList.add(paths);
            return;
        }

        // 递归调用
        coreRecGetAllPaths(fromNode, toNode, new ArrayDeque<>());
    }

    private void coreRecGetAllPaths(DirectedDiagraph.Vertex fromVertex, DirectedDiagraph.Vertex toVertex, Deque<String> nowPaths) {
        // 检查是否存在环，跳过
        if (nowPaths.contains(fromVertex.getId())) {
            System.out.println("存在环");
            // 出栈
            nowPaths.pop();
            return;
        }

        // 当前路径加上其实节点
        nowPaths.push(fromVertex.getId());
        // 深度搜索边
        for (DirectedDiagraph.Edge edge : fromVertex.getEdges()) {
            // 如果边的目标顶点和路径的最终节点一直，表示找到成功
            if (StrUtil.equals(edge.getTargetVertexId(), toVertex.getId())) {
                // 将数据添加到当前路径中
                nowPaths.push(toVertex.getId());
                // 拷贝一份数据放到allPathList中
                List<String> findPaths = new ArrayList<>();
                findPaths.addAll(nowPaths);
                CollUtil.reverse(findPaths);
                allPathList.add(findPaths);
                // 加入了最终节点，返回一次
                nowPaths.pop();
                // 跳过，查询下一个边
                continue;
            }

            // 以边的目标顶点作为其实顶点，继续搜索
            DirectedDiagraph.Vertex nextFromVertex = directedDiagraph.getVertextMap().get(edge.getTargetVertexId());
            if (nextFromVertex == null) {
                throw new RuntimeException("顶点id不存在");
            }
            // 递归调用下一次
            coreRecGetAllPaths(nextFromVertex, toVertex, nowPaths);
        }

        // 结束了，没找到，弹出数据
        nowPaths.pop();
    }

}
