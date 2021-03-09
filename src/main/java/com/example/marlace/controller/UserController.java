package com.example.marlace.controller;

import com.example.marlace.model.User;
import com.example.marlace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/u")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        final String firstName = (String) userMap.get("firstName");
        final String lastName = (String) userMap.get("lastName");
        final String email = (String) userMap.get("email");
        final String password = (String) userMap.get("password");

        User user = userService.registerUser(firstName, lastName, email, password);
        Map<String, String> map = new HashMap<>();
        map.put("message", "registered successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
