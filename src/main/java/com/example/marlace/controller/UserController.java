package com.example.marlace.controller;

import com.example.marlace.model.User;
import com.example.marlace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.example.marlace.utils.Utils.generateJWTToken;

@RestController
@RequestMapping("/api/u")
public class UserController {

    @Autowired
    UserService userService;

    private final String applicationJSON = "application/json";

    @GetMapping(consumes = applicationJSON, produces = applicationJSON)
    public ResponseEntity<User> getAuthenticatedUser(HttpServletRequest request) {
        final Integer userId = (Integer) request.getAttribute("userId");
        final User user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/login", consumes = applicationJSON, produces = applicationJSON)
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        final String email = (String) userMap.get("email");
        final String password = (String) userMap.get("password");
        final User user = userService.authorizeUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping(path = "/register", consumes = applicationJSON, produces = applicationJSON)
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        final String firstName = (String) userMap.get("firstName");
        final String lastName = (String) userMap.get("lastName");
        final String email = (String) userMap.get("email");
        final String password = (String) userMap.get("password");
        final User user = userService.registerUser(firstName, lastName, email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete", consumes = applicationJSON, produces = applicationJSON)
    public ResponseEntity<Map<String, Object>> deleteUser(HttpServletRequest request,
                                                          @RequestBody Map<String, Object> body) {
        final Integer userId = (Integer) request.getAttribute("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("request", request);
        map.put("body", body);
        return new ResponseEntity<>(map, HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping(path = "/update", consumes = applicationJSON, produces = applicationJSON)
    public ResponseEntity<Map<String, Object>> updatedUser(HttpServletRequest request,
                                                          @RequestBody Map<String, Object> body) {
        final Integer userId = (Integer) request.getAttribute("userId");
        System.err.println(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("request", request);
        map.put("body", body);
        return new ResponseEntity<>(map, HttpStatus.NOT_IMPLEMENTED);
    }
}
