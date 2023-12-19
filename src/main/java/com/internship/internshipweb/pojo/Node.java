package com.internship.internshipweb.pojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public List<UserNode> getUserNodes() {
        UserRepository userRepository = new UserRepository();
        return userRepository.getUserNodesByNodeId(this.nodeId);

    }
}
