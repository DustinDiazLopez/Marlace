package com.example.marlace.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Category {
    private Integer categoryId;
    private Integer userId;
    private String title;
    private String description;
    private Double totalExpenses;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Category() {}

    public Category(Integer categoryId, Integer userId, String title, String description, Double totalExpenses) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.totalExpenses = totalExpenses;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
