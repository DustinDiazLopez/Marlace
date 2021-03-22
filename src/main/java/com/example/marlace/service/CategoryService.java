package com.example.marlace.service;

import com.example.marlace.exceptions.MarlaceBadRequestException;
import com.example.marlace.exceptions.MarlaceResourceNotFoundException;
import com.example.marlace.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategories(Integer userId);
    Category fetchCategoryById(Integer userId, Integer categoryId) throws MarlaceResourceNotFoundException;
    Category addCategory(Integer userId, String title, String description) throws MarlaceBadRequestException;
    void updateCategory(Integer userId, Integer categoryId, Category category) throws MarlaceBadRequestException;
    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws MarlaceResourceNotFoundException;
}
