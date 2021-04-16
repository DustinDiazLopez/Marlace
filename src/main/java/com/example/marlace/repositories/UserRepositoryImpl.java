package com.example.marlace.repositories;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.models.User;
import com.example.marlace.repositories.mappers.UserRowMapper;
import com.example.marlace.utilities.DB;
import com.example.marlace.utilities.Database;
import com.example.marlace.utilities.Utils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_INSERT_USER = "INSERT INTO `user`(`firstName`, `lastName`, `email`, `password`) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM `user` WHERE `userId` = ?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM `user` WHERE `email` = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM `user` WHERE userId = ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE `user` SET `firstName` = ?, `lastName` = ?, `email` = ?, `password` = ?, `description` = ? WHERE userId = ?";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    JdbcTemplate jdbcTemplate;

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    private final DB database = new DB(sessionFactory);

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws MarlaceAuthException {
        final String hashedPassword = Utils.hashPassword(password);

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                int paramIndex = 0;
                ps.setString(++paramIndex, firstName);
                ps.setString(++paramIndex, lastName);
                ps.setString(++paramIndex, email);
                ps.setString(++paramIndex, hashedPassword);
                return ps;
            }, keyHolder);

            if (keyHolder.getKeys() != null) {
                // TODO: FIX the value is being converted from a a big integer to a string and to a integer
                final String userId = keyHolder.getKeys().get("GENERATED_KEY").toString();
                log.info("Created user with id: " + userId);
                return Integer.parseInt(userId);
            } else {
                throw new MarlaceAuthException("Failed to create the user account.");
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof NumberFormatException) {
                final String errorMsg = "Failed to convert big integer to int";
                log.error(errorMsg);
                throw new MarlaceAuthException(errorMsg);
            }

            throw new MarlaceAuthException("Invalid details. Failed to create the user account.");
        }
    }

    @Override
    public Boolean delete(Integer id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            int paramIndex = 0;
            ps.setInt(++paramIndex, id);
            return ps;
        });

        final User user = findById(id);
        final boolean deleted = user == null;
        log.info(String.format("User with id '%d' was deleted: %s", id, deleted));
        return deleted;
    }

    @Override
    public Boolean update(Integer id, String firstName, String lastName, String email, String password,
                          String description) throws MarlaceAuthException {
        if (id == null) {
            throw new MarlaceAuthException("id cannot be null");
        }

        User oldUser = this.findById(id);

        firstName = Utils.nullOrEmpty(firstName);
        if (firstName == null) {
            firstName = oldUser.getFirstName();
        }

        lastName = Utils.nullOrEmpty(lastName);
        if (lastName == null) {
            lastName = oldUser.getLastName();
        }

        email = Utils.nullOrEmpty(email);
        if (email == null) {
            email = oldUser.getEmail();
        }

        if (!Utils.matchesEmail(email)) {
            throw new MarlaceAuthException("the email is not valid");
        }

        if (password == null) {
            password = oldUser.getPassword();
        }

        final String OLD_PASSWORD = oldUser.getPassword();
        final String POTENTIALLY_NEW_PASSWORD = password;

        try {
            final String finalFirstName = firstName;
            final String finalLastName = lastName;
            final String finalEmail = email;
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
                int paramIndex = 0;
                ps.setString(++paramIndex, finalFirstName);
                ps.setString(++paramIndex, finalLastName);
                ps.setString(++paramIndex, finalEmail);

                // if the old password matches the inputted password set the password as the same
                // else hash the new inputted password
                if (!Utils.checkPassword(POTENTIALLY_NEW_PASSWORD, OLD_PASSWORD)) {
                    ps.setString(++paramIndex, OLD_PASSWORD);
                } else {
                    ps.setString(++paramIndex, Utils.hashPassword(POTENTIALLY_NEW_PASSWORD));
                }

                ps.setString(++paramIndex, description);
                ps.setInt(++paramIndex, id);
                return ps;
            });
            log.info(String.format("User with id '%d' was updated.", id));
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarlaceAuthException("Invalid details. Failed to update user.");
        }
    }

    @Override
    public User findById(Integer userId) {
        return Database.get(sessionFactory, User.class, userId);
    }

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_EMAIL, userRowMapper, email);
    }

    @Override
    public User authenticate(String email, String password) throws MarlaceAuthException {
        try {
            final User user = this.findByEmail(email);

            if (!Utils.checkPassword(password, user.getPassword())) {
                throw new MarlaceAuthException("Invalid email and/or password.");
            } else {
                return user;
            }
        } catch (EmptyResultDataAccessException ignored) {
            throw new MarlaceAuthException("Invalid email and/or password.");
        }
    }

    @Override
    public Boolean emailExists(String email) {
        final List<User> users = jdbcTemplate.query(
                SQL_SELECT_USER_BY_EMAIL,
                new Object[]{email},
                new int[]{Types.VARCHAR},
                userRowMapper
        );

        return users.size() > 0;
    }
}
