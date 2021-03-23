package com.example.marlace.repository;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.model.User;
import com.example.marlace.utils.Utils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_INSERT_USER = "INSERT INTO `users`(`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM `users` WHERE `user_id` = ?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM `users` WHERE `email` = ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE users SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ?, `description` = ? WHERE user_id = ?";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    JdbcTemplate jdbcTemplate;

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
            ps.setInt(1, id);
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
        final String hashedPassword = Utils.hashPassword(password);

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
                int paramIndex = 0;
                ps.setString(++paramIndex, firstName);
                ps.setString(++paramIndex, lastName);
                ps.setString(++paramIndex, email);
                ps.setString(++paramIndex, hashedPassword);
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
        return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID, userRowMapper, userId);
    }

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_EMAIL, userRowMapper, email);
    }

    @Override
    public User authenticate(String email, String password) throws MarlaceAuthException {
        try {
            final User user = findByEmail(email);

            if (!BCrypt.checkpw(password, user.getPassword())) {
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
                new Object[]{ email },
                new int[]{ Types.VARCHAR },
                userRowMapper
        );

        return users.size() > 0;
    }
}
