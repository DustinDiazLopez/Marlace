package com.example.marlace.service;

import com.example.marlace.exception.EtAuthException;
import com.example.marlace.model.User;

public interface UserService {
    User validateUser(String fistName, String lastName, String email, String password) throws EtAuthException;
    User registerUser(String fistName, String lastName, String email, String password) throws EtAuthException;

}
