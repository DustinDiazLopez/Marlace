package com.example.marlace.service;

import com.example.marlace.exceptions.MarlaceBadRequestException;
import com.example.marlace.exceptions.MarlaceResourceNotFoundException;
import com.example.marlace.model.Category;
import com.example.marlace.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(Integer userId) {
        return null;
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId) throws MarlaceResourceNotFoundException {
        return null;
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws MarlaceBadRequestException {
        final Integer categoryId = categoryRepository.create(userId, title, description);
        System.err.println(categoryId);
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws MarlaceBadRequestException {

    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws MarlaceResourceNotFoundException {

    }
}
