package com.alvin.error.manager;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author sofn
 * @version 1.0 Created at: 2022-03-10 11:46
 */
@Getter
@ToString
@EqualsAndHashCode(of = "code")
public class TreeNode {
    int code;
    String name;
    List<TreeNode> nodes;

    public TreeNode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }
}
