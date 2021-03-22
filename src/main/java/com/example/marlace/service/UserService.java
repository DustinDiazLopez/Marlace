package com.example.marlace.service;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.model.User;

public interface UserService {
    User authorizeUser(String email, String password) throws MarlaceAuthException;
    User registerUser(String fistName, String lastName, String email, String password) throws MarlaceAuthException;

}
