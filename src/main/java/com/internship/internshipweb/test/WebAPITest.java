package com.internship.internshipweb.test;
import com.internship.internshipweb.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.server.*;
import org.springframework.http.*;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebAPITest {

//    @Test
//    public void testCreateUserNodeRelation() {
//        int userId = 1; // 替换为实际的用户ID
//        int nodeId = 1; // 替换为实际的节点ID
//        String url = "http://localhost:" + port + "/api/users/" + userId + "/nodes/" + nodeId;
//        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        // TODO: 验证用户节点关联是否成功创建
//    }



    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();


    @MockBean
    private UserRepository userRepository;

    @Test
    public void testAddUserToNode() {
        User user = new User(1, "John", UserType.ADMIN);
        Node node = new Node(1, "Root", 0);

        // 模拟 UserRepository 的行为
        UserNode userNode = new UserNode(user.getUserId(), node.getNodeId());
        List<UserNode> userNodes = new ArrayList<>();
        userNodes.add(userNode);
        Mockito.when(userRepository.findUser(user.getUserId())).thenReturn(user);
        Mockito.when(userRepository.getUserNodesByNodeId(node.getNodeId())).thenReturn((List<UserNode>) userNodes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> userRequest = new HttpEntity<>(user, headers);
        ResponseEntity<Node> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/nodes/{nodeId}/users/{userId}",
                HttpMethod.POST, userRequest, Node.class,
                node.getNodeId(), user.getUserId());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getUserNodes().size());
        assertEquals("John", response.getBody().getUserNodes().get(0).getUser().getUserName());
        assertEquals(UserType.ADMIN, response.getBody().getUserNodes().get(0).getUser().getUserType());
    }

}

