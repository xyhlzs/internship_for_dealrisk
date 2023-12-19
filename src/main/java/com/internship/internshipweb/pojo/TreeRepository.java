
package com.internship.internshipweb.pojo;

        import java.sql.*;

public class TreeRepository {
    private final String url = "jdbc:postgresql://localhost:5432/internsh";
    private final String user = "postgres";
    private final String password = "891466";
    private Connection connection;
    public TreeRepository() {
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



    //查找树
    public Tree findTree(int treeId){
        Tree tree = new Tree();
        try {
            String sql = "SELECT * FROM trees WHERE tree_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,treeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                tree.setTreeId(resultSet.getInt("tree_id"));
                tree.setTreeName(resultSet.getString("tree_name"));
                return tree;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tree;
    }
    //删除所有树
    public void deleteAllTree(){
        try {
            String sql1 = "DELETE FROM trees";
            String sql2 = "DELETE FROM nodes";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}