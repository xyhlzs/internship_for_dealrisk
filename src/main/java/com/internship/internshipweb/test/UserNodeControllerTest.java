package com.internship.internshipweb.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.internshipweb.InternshipWebApplication;
import com.internship.internshipweb.pojo.User;
import com.internship.internshipweb.pojo.UserNode;
import com.internship.internshipweb.pojo.UserRepository;
import com.internship.internshipweb.pojo.UserType;
import com.internship.internshipweb.util.TreeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.runtime.ObjectMethods;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = InternshipWebApplication.class)
@AutoConfigureMockMvc
public class UserNodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreeUtils treeUtils;

    @Test
    public void testAddUserNode() throws Exception {
        // 假设存在一个管理员用户
        int adminUserId = 1;
        User user = new User(adminUserId,"admin", UserType.ADMIN);
        UserRepository userRepository = new UserRepository();
        userRepository.addUser(user);
        UserNode userNode = new UserNode();

        userNode.setUserId(2); // 使用实际的用户ID
        userNode.setNodeId(3); // 使用实际的节点ID
        ObjectMapper objectMapper  = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(userNode);
        // 将userNode转换为JSON字符串

        mockMvc.perform(post("/api/usernodes/add?adminUserId=" + adminUserId)
                                .contentType("application/json")
                                .content(jsonRequest))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User-node association created successfully."));
    }


}