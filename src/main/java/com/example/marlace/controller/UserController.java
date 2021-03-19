package com.example.marlace.controller;

import com.example.marlace.utils.Constants;
import com.example.marlace.model.User;
import com.example.marlace.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/u")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        final String email = (String) userMap.get("email");
        final String password = (String) userMap.get("password");
        final User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        final String firstName = (String) userMap.get("firstName");
        final String lastName = (String) userMap.get("lastName");
        final String email = (String) userMap.get("email");
        final String password = (String) userMap.get("password");
        final User user = userService.registerUser(firstName, lastName, email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.CREATED);
    }

    private Map<String, String> generateJWTToken(final User user) {
        final long timestamp = System.currentTimeMillis();
        final String token = Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS512, Constants.JWT.JWT_SECRET_KEY)
                .setIssuedAt(new Date(timestamp + Constants.JWT.TOKEN_DURATION))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        final Map<String, String> map = new HashMap<>(1);
        map.put("token", token);
        return Collections.unmodifiableMap(map);
    }

}
