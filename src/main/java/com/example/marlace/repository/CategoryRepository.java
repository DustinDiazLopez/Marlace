package com.example.marlace.repository;

import com.example.marlace.exceptions.MarlaceBadRequestException;
import com.example.marlace.exceptions.MarlaceResourceNotFoundException;
import com.example.marlace.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws MarlaceResourceNotFoundException;
    Category findById(Integer userId, Integer categoryId) throws MarlaceResourceNotFoundException;
    Integer create(Integer userId, String title, String description) throws MarlaceBadRequestException;
    Integer createCategory(Integer userId, String title, String description) throws MarlaceBadRequestException;
    void update(Integer userId, Integer categoryId, Category category) throws MarlaceBadRequestException;
    void removeById(Integer userId, Integer categoryId);

}
