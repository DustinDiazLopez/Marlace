package com.example.marlace.service;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.exceptions.MarlaceNullParamException;
import com.example.marlace.model.User;
import com.example.marlace.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.marlace.utils.Constants.Patterns.EMAIL_PATTERN;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Integer id) throws MarlaceNullParamException {
        if (id == null) {
            throw new MarlaceNullParamException("'id' cannot be null");
        }
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) throws MarlaceNullParamException {
        if (email == null || email.isEmpty()) {
            throw new MarlaceNullParamException("'email' cannot be null not empty");
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

            if (!EMAIL_PATTERN.matcher(email).matches()) {
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
