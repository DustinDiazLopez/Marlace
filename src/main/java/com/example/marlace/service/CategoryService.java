package com.example.marlace.service;

import com.example.marlace.exceptions.EtBadRequestException;
import com.example.marlace.exceptions.EtResourceNotFoundException;
import com.example.marlace.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategories(Integer userId);
    Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    Category addCategory(Integer userId, String title, String description) throws EtBadRequestException;
    void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;
    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
}
