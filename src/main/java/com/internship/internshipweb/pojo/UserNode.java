package com.internship.internshipweb.pojo;

public class UserNode {
    private int userId;
    private int nodeId;

    public UserNode(int userId, int nodeId) {
        this.userId = userId;
        this.nodeId = nodeId;
    }

    public UserNode() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return "UserNode{" +
                "userId=" + userId +
                ", nodeId=" + nodeId +
                '}';
    }

    public User getUser() {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.findUser(getUserId());
        return user;
    }


}
