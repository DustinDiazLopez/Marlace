package com.example.marlace.controllers;

import com.example.marlace.models.User;
import com.example.marlace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.marlace.utilities.Constants.APPLICATION_JSON;

@RestController
public class IndexController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "Hello, World!";
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getAllCategories(HttpServletRequest request) {
        final Integer userId = (Integer) request.getAttribute("userId");
        final User user = this.userService.findById(userId);
        return new ResponseEntity<>(String.format("Hello, %s!", user.getFirstName()), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}", produces = APPLICATION_JSON)
    public ResponseEntity<User> test(@PathVariable("userId") final Integer userId) {
        final User user = userService.findById(userId);

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        user.censor();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
