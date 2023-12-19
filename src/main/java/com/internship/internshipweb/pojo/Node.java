package com.internship.internshipweb.pojo;

public class Node {
    private int nodeId;
    private String nodeName;
    private int parentId;

    public Node(int nodeId, String nodeName, int parentId) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentId = parentId;
    }

    public Node() {

    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeId=" + nodeId +
                ", nodeName='" + nodeName + '\'' +
                ", treeId=" + parentId +
                '}';
    }
}
