package com.internship.internshipweb.test;

import com.internship.internshipweb.pojo.*;
import com.internship.internshipweb.pojo.UserType;
import com.internship.internshipweb.util.TreeUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.*;
import static org.junit.Assert.*;

public class MainTest {
    private final String url = "jdbc:postgresql://localhost:5432/internsh";
    private final String user = "postgres";
    private final String password = "891466";

    private Connection connection;
    public MainTest() {
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
    // 在每个测试方法之前执行的操作
    @Before
    public void setUp() {
        // 设置测试环境
    }

    // 在每个测试方法之后执行的操作
    @After
    public void tearDown() {
        // 清理测试环境
        UserRepository userRepository = new UserRepository();
        userRepository.deleteAllUser_Node();
        userRepository.deleteAllUser();

        TreeRepository treeRepository = new TreeRepository();
        treeRepository.deleteAllTree();
    }



    @Test
    public void testSaveUser() {
        // 创建本地用户并保存
        int userId = 2;
        String userName = "jack";
        UserType userType = UserType.ADMIN;
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserType(userType);


        UserRepository userRepository = new UserRepository();
        userRepository.addUser(userId, userName, userType);

        // 根据用户ID查询用户
        User savedUser = userRepository.findUser(userId);


        // 验证用户是否保存成功
        Assert.assertEquals(user.getUserType(), savedUser.getUserType());
        Assert.assertEquals(user.getUserId(),savedUser.getUserId());
        Assert.assertEquals(user.getUserName(),savedUser.getUserName());
    }

    @Test
    public void testSaveTree() {

        // 创建 N 叉树并保存
        Tree tree = new Tree(1,"tree1");

        int userId = 1;
        String userName = "polo";
        UserType userType = UserType.ADMIN;
        User user = new User(userId,userName,userType);

        TreeUtils treeUtils = new TreeUtils();
        treeUtils.addTree(tree,user);


        // 根据树ID查询树
        TreeRepository treeRepository = new TreeRepository();
        Tree savedTree = treeRepository.findTree(1);

        // 验证树是否保存成功
        Assert.assertEquals(tree.getTreeId(), savedTree.getTreeId());
        Assert.assertEquals(tree.getTreeName(), savedTree.getTreeName());
    }

    @Test
    public void testAssociateUserWithTreeNode() {
        // 创建用户和树节点
        User user = new User(23,"John", UserType.ADMIN);
        UserRepository userRepository = new UserRepository();
        userRepository.addUser(user);

        Tree tree = new Tree(1,"tree1");
        Node node1 = new Node(1,"Node1",1);
        Node node2 = new Node(2,"Node2",1);


        TreeUtils treeUtils = new TreeUtils();
        treeUtils.addTree(tree,user);
        treeUtils.addNode(node1,user);
        treeUtils.addNode(node2,user);

        // 关联用户和树节点
        userRepository.addUserNode(user.getUserId(), node1.getNodeId());
        userRepository.addUserNode(user.getUserId(), node2.getNodeId());

        // 根据用户ID和节点ID查询关联
        UserNode userNode1 = treeUtils.findUserNode(user.getUserId(), node1.getNodeId());
        UserNode userNode2 = treeUtils.findUserNode(user.getUserId(), node2.getNodeId());

        // 验证关联是否成功
        Assert.assertNotNull(userNode1);
        Assert.assertNotNull(userNode2);
        Assert.assertEquals(user.getUserId(), userNode1.getUserId());
        Assert.assertEquals(user.getUserId(), userNode2.getUserId());
        Assert.assertEquals(node1.getNodeId(), userNode1.getNodeId());
        Assert.assertEquals(node2.getNodeId(), userNode2.getNodeId());
    }


}
