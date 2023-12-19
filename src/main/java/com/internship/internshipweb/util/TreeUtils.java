package com.internship.internshipweb.util;

import com.internship.internshipweb.pojo.*;
import com.internship.internshipweb.pojo.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TreeUtils {
    private  Connection connection;

    public TreeUtils() {
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
    public void addUser(int userId, String userName, UserType userType){
        try {
            String sql = "INSERT INTO users (user_name) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.setString(2, userName);
            statement.setString(3, String.valueOf(userType));
            statement.executeUpdate();
            System.out.println("已添加用户");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查找用户
    public User findUser(int userId){
        try {
            String sql = "SELECT * FROM users WHERE user_id = (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserType (UserType.valueOf(resultSet.getString("user_type"))) ;
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 添加树
    public  void addTree(Tree tree, User user) {
        if (user.getUserType() == UserType.ADMIN) {
            try {
                String sql = "INSERT INTO trees (tree_id,tree_name) VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, tree.getTreeId());
                statement.setString(2, tree.getTreeName());
                statement.executeUpdate();
                System.out.println("已添加树");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("权限不足");
        }
    }

    // 增加节点
    public void addNode(Node node, User user) {
        if (user.getUserType() == UserType.ADMIN) {
            try {
                String sql = "INSERT INTO nodes (node_id,node_name, parent_id) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,node.getNodeId());
                statement.setString(2, node.getNodeName());
                statement.setInt(3, node.getParentId());
                statement.executeUpdate();
                System.out.println("成功添加节点");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("权限不足");
        }
    }

    // 删除节点
    public void deleteNode(int nodeId, User user) {
        if (user.getUserType() == UserType.ADMIN) {
            try {
                String sql = "DELETE FROM nodes WHERE node_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, nodeId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("已删除节点");
                } else {
                    System.out.println("未找到节点");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("权限不足");
        }
    }

    // 根据节点ID查找节点
    public Node findNodeById(int nodeId) {
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

    //根据树id查找树
    public Tree getTrees(int treeId) {
        try {
            String sql = "SELECT * FROM trees WHERE tree_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, treeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Tree tree = new Tree();
                tree.setTreeId(resultSet.getInt("tree_id"));
                tree.setTreeName(resultSet.getString("tree_name"));
                return tree;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 通过用户查询树的节点
    public List<Node> getTreeNodes(User user) {

        List<Node> nodes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM nodes JOIN user_nodes ON nodes.node_id = user_nodes.node_id WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Node node = new Node();
                node.setNodeId(resultSet.getInt("node_id"));
                node.setNodeName(resultSet.getString("node_name"));
                node.setParentId(resultSet.getInt("parent_id"));
                nodes.add(node);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nodes;

    }

    // 通过树节点查询用户
    public List<User> getUserNodes(Node node) {

        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users JOIN user_nodes ON users.user_id = user_nodes.user_id WHERE user_nodes.node_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, node.getNodeId());
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserType(UserType.valueOf(resultSet.getString("user_type")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }


    // 增加用户与节点
    public void addUserNode(UserNode userNode, User user) {
        if (user.getUserType() == UserType.ADMIN) {
            try {
                String sql = "INSERT INTO user_nodes (user_id, node_id) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userNode.getUserId());
                statement.setInt(2, userNode.getNodeId());
                statement.executeUpdate();
                System.out.println("用户-树节点添加成功");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("权限不足");
        }
    }

    // 删除用户与节点
    public void deleteUserNode(int userId, int nodeId, User user) {
        if (user.getUserType() == UserType.ADMIN) {
            try {
                String sql = "DELETE FROM user_nodes WHERE user_id = ? AND node_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.setInt(2, nodeId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("成功删除用户-树节点");
                } else {
                    System.out.println("无法找到相应的用户-树节点");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("权限不足");
        }
    }

    // 根据用户ID和节点ID查找用户与节点的关联
    public UserNode findUserNode(int userId, int nodeId) {
        try {
            String sql = "SELECT * FROM user_nodes WHERE user_id = ? AND node_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, nodeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserNode userNode = new UserNode();
                userNode.setUserId(resultSet.getInt("user_id"));
                userNode.setNodeId(resultSet.getInt("node_id"));
                return userNode;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

