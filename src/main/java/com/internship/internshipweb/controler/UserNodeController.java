package com.internship.internshipweb.controler;

import com.internship.internshipweb.pojo.*;
import com.internship.internshipweb.util.TreeUtils;
import com.internship.internshipweb.pojo.User;
import com.internship.internshipweb.pojo.UserNode;
import com.internship.internshipweb.pojo.UserType;
import com.internship.internshipweb.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usernodes")
public class UserNodeController {

    private final TreeUtils treeUtils;

    @Autowired
    public UserNodeController(TreeUtils treeUtils) {
        this.treeUtils = treeUtils;
    }

    // 创建用户与节点的关联
    @PostMapping("/add")
    public ResponseEntity<?> addUserNode(@RequestBody UserNode userNode, @RequestParam int adminUserId) {
        User admin = treeUtils.findUser(adminUserId);
        if (admin != null && admin.getUserType() == UserType.ADMIN) {
            treeUtils.addUserNode(userNode, admin);
            return ResponseEntity.ok("User-node association created successfully.");
        }
        return ResponseEntity.status(403).body("Unauthorized: Only admins can create associations.");
    }


}