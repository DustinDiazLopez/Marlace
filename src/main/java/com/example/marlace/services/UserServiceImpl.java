package com.example.marlace.services;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.exceptions.MarlaceBadRequestException;
import com.example.marlace.models.User;
import com.example.marlace.repositories.UserRepository;
import com.example.marlace.utilities.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Integer id) throws MarlaceBadRequestException {
        if (id == null) {
            throw new MarlaceBadRequestException("'id' cannot be null");
        }
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) throws MarlaceBadRequestException {
        if (email == null || email.isEmpty()) {
            throw new MarlaceBadRequestException("'email' cannot be null not empty");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public User authorizeUser(String email, String password) throws MarlaceAuthException {
        if (email != null) email = email.toLowerCase();
        else throw new MarlaceAuthException("Email cannot be null");
        if (password == null) throw new MarlaceAuthException("Password cannot be null");
        return userRepository.authenticate(email, password);
    }

    @Override
    public User registerUser(String fistName, String lastName, String email, String password) throws MarlaceAuthException {
        if (email != null) {
            email = email.toLowerCase();

            if (!Utils.matchesEmail(email)) {
                throw new MarlaceAuthException("Invalid email format.");
            }

            final Boolean emailExists = userRepository.emailExists(email);

            if (emailExists) {
                throw new MarlaceAuthException("Email already in use.");
            }

            final Integer userId = userRepository.create(fistName, lastName, email, password);
            return userRepository.findById(userId);
        } else {
            throw new MarlaceAuthException("Email cannot be null.");
        }
    }
}
