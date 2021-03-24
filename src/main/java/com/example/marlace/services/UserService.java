package com.example.marlace.services;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.exceptions.MarlaceBadRequestException;
import com.example.marlace.models.User;

public interface UserService {
    User findById(Integer id) throws MarlaceBadRequestException;

    User findByEmail(String email) throws MarlaceBadRequestException;

    User authorizeUser(String email, String password) throws MarlaceAuthException;

    User registerUser(String fistName, String lastName, String email, String password) throws MarlaceAuthException;
}
