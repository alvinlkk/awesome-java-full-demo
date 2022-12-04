/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.datastruct;

import java.util.Set;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/26
 * @since 1.0
 **/
@Slf4j(topic = "a.DirectedDiagraphTest")
public class DirectedDiagraphTest {

    @Test
    public void test1() {
        DirectedDiagraph directedDiagraph = new DirectedDiagraph();
        directedDiagraph.addVertex("A", "A");
        directedDiagraph.addVertex("B", "B");
        directedDiagraph.addVertex("C", "C");
        directedDiagraph.addVertex("D", "D");
        directedDiagraph.addVertex("E", "E");

        directedDiagraph.addEdge("A", "B");
        directedDiagraph.addEdge("B", "C");
        directedDiagraph.addEdge("C", "D");
        directedDiagraph.addEdge("A", "D");
        directedDiagraph.addEdge("B", "D");
        directedDiagraph.addEdge("A", "C");
        directedDiagraph.addEdge("D", "E");

        Set<String> allPaths = directedDiagraph.findAllPaths("A", "D");
        log.info("all vertexIds: {}", allPaths);
    }
}
