package com.internship.internshipweb.test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.server.*;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testCreateUserNodeRelation() {
        int userId = 1; // 替换为实际的用户ID
        int nodeId = 1; // 替换为实际的节点ID
        String url = "http://localhost:" + port + "/api/users/" + userId + "/nodes/" + nodeId;
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // TODO: 验证用户节点关联是否成功创建
    }


}

