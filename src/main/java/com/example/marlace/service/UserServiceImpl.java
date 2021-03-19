package com.example.marlace.service;

import com.example.marlace.exceptions.EtAuthException;
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
