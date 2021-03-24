package com.example.marlace.controllers;

import com.example.marlace.models.User;
import com.example.marlace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.example.marlace.utilities.Constants.APPLICATION_JSON;
import static com.example.marlace.utilities.Utils.generateJWTToken;

@RestController
@RequestMapping("/api/u")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<User> getAuthenticatedUser(HttpServletRequest request) {
        final Integer authUserId = (Integer) request.getAttribute("userId");
        final User user = userService.findById(authUserId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/login", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> requestBody) {
        final String email = (String) requestBody.get("email");
        final String password = (String) requestBody.get("password");
        final User user = userService.authorizeUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping(path = "/register", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> requestBody) {
        final String firstName = (String) requestBody.get("firstName");
        final String lastName = (String) requestBody.get("lastName");
        final String email = (String) requestBody.get("email");
        final String password = (String) requestBody.get("password");
        final User user = userService.registerUser(firstName, lastName, email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> deleteUser(HttpServletRequest request,
                                                          @RequestBody Map<String, Object> requestBody) {
        final Integer userId = (Integer) request.getAttribute("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("request", request);
        map.put("body", requestBody);
        return new ResponseEntity<>(map, HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping(path = "/update", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> updatedUser(HttpServletRequest request,
                                                           @RequestBody Map<String, Object> requestBody) {
        final Integer userId = (Integer) request.getAttribute("userId");
        System.err.println(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("request", request);
        map.put("body", requestBody);
        return new ResponseEntity<>(map, HttpStatus.NOT_IMPLEMENTED);
    }
}
