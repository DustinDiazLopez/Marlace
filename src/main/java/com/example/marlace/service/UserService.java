package com.example.marlace.service;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.exceptions.MarlaceNullParamException;
import com.example.marlace.model.User;

public interface UserService {
    User findById(Integer id) throws MarlaceNullParamException;

    User findByEmail(String email) throws MarlaceNullParamException;

    User authorizeUser(String email, String password) throws MarlaceAuthException;

    User registerUser(String fistName, String lastName, String email, String password) throws MarlaceAuthException;

}
