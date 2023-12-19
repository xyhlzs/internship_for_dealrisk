package com.internship.internshipweb.pojo;

import org.springframework.context.annotation.Bean;

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

    private  Connection connection;
    public Node findNodeById(int nodeId){
        try {
            String sql = "SELECT * FROM nodes WHERE node_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, nodeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Node node = new Node();
                node.setNodeId(resultSet.getInt("node_id"));
                node.setNodeName(resultSet.getString("node_name"));
                node.setParentId(resultSet.getInt("parent_id"));
                return node;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
