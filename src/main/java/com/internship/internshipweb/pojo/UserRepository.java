package com.internship.internshipweb.pojo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private Connection connection;

    public UserRepository() {
        try {
            // JDBC
            String url = "jdbc:postgresql://localhost:5432/internsh";
            String username = "postgres";
            String password = "891466";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加用户
    public void addUser(int userId, String userName, UserType userType) {
        try {
            String sql = "INSERT INTO users (user_id,user_name,user_type) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2, userName);
            statement.setString(3, String.valueOf(userType));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            String sql = "INSERT INTO users (user_id,user_name,user_type) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, String.valueOf(user.getUserType()));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查找用户
    public User findUser(int userId) {
        User user = new User();
        try {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserType(UserType.valueOf(resultSet.getString("user_type")));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //添加用户-树节点
    public void addUserNode(int userId, int nodeId) {
        try {
            String sql = "INSERT INTO user_nodes (user_id,node_id) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, nodeId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserNode(User user, Node node) {
        try {
            String sql = "INSERT INTO user_nodes (user_id,node_id) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getUserId());
            statement.setInt(2, node.getNodeId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //删除所有用户
    public void deleteAllUser() {
        try {
            String sql = "DELETE FROM users";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除所有节点关联
    public void deleteAllUser_Node() {
        try {
            String sql = "DELETE FROM user_nodes";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //通过结点查找关系
    public List<UserNode> getUserNodesByNodeId(int nodeId) {
        List<UserNode> userNodes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user_nodes WHERE node_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, nodeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserNode userNode = new UserNode();
                userNode.setUserId(resultSet.getInt("user_id"));
                userNode.setNodeId(resultSet.getInt("node_id"));
                userNodes.add(userNode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNodes;
    }
}