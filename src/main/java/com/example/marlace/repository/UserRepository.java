package com.example.marlace.repository;

import com.example.marlace.exceptions.EtAuthException;
import com.example.marlace.model.User;

public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;
    User findUserByEmailAndPassword(String email, String password) throws EtAuthException;
    Boolean emailExists(String email);
    User findById(Integer id);
}
