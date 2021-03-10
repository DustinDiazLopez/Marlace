package com.example.marlace.controller;

import com.example.marlace.model.Category;
import com.example.marlace.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String getAllCategories(HttpServletRequest request) {
        final Integer userId = (Integer) request.getAttribute("userId");
        return "Authenticated! UserId: " + userId;
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(HttpServletRequest request, @RequestBody Map<String, Object> body) {
        final Integer userId = (Integer) request.getAttribute("userId");
        final String title = (String) body.get("title");
        final String description = (String) body.get("description");
        final Category category = categoryService.addCategory(userId, title, description);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
