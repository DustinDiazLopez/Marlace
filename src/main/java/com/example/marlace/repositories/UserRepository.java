package com.example.marlace.repositories;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.models.User;

public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password) throws MarlaceAuthException;
    Boolean delete(Integer id) throws MarlaceAuthException;
    Boolean update(Integer id, String firstName, String lastName, String email, String password, String description) throws MarlaceAuthException;
    User findById(Integer id);
    User findByEmail(String email);
    User authenticate(String email, String password) throws MarlaceAuthException;
    Boolean emailExists(String email);
}
