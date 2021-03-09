package com.example.marlace.service;

import com.example.marlace.exception.EtAuthException;
import com.example.marlace.model.User;
import com.example.marlace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if (email != null) email = email.toLowerCase();
        else throw new EtAuthException("Email cannot be null");
        if (password == null) throw new EtAuthException("Password cannot be null");
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String fistName, String lastName, String email, String password) throws EtAuthException {
        if (email != null) {
            email = email.toLowerCase();

            if (!EMAIL_PATTERN.matcher(email).matches()) {
                throw new EtAuthException("Invalid email format.");
            }

            final Boolean emailExists = userRepository.emailExists(email);

            if (emailExists) {
                throw new EtAuthException("Email already in use.");
            }

            final Integer userId = userRepository.create(fistName, lastName, email, password);
            return userRepository.findById(userId);
        } else {
            throw new EtAuthException("Email cannot be null.");
        }
    }
}
