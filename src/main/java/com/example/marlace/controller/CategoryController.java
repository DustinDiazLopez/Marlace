package com.example.marlace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public String getAllCategories(HttpServletRequest request) {
        final int userId = (int) request.getAttribute("userId");
        return "Authenticated! UserId: " + userId;
    }
}
