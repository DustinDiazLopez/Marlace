package com.example.marlace.repository;

import com.example.marlace.exceptions.EtAuthException;
import com.example.marlace.mappers.UserRowMapper;
import com.example.marlace.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_INSERT_USER = "INSERT INTO `users`(`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?)";
    private static final String SQL_COUNT_USER_EMAIL = "SELECT COUNT(*) FROM `users` WHERE `email` = ?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM `users` WHERE `user_id` = ?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM `users` WHERE `email` = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
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
                final String USER_ID = keyHolder.getKeys().get("GENERATED_KEY").toString().trim();
                return Integer.parseInt(USER_ID);
            } else {
                throw new EtAuthException("Failed to create the user account.");
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof NumberFormatException) {
                throw new EtAuthException("Failed to convert string to int.");
            }

            throw new EtAuthException("Invalid details. Failed to create the user account.");
        }
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws EtAuthException {
        try {
            final List<User> users = jdbcTemplate.query(
                    SQL_SELECT_USER_BY_EMAIL,
                    new Object[]{email},
                    new int[]{},
                    new UserRowMapper()
            );

            System.err.println(users.size());

            if (users.size() == 1) {
                if (!BCrypt.checkpw(password, users.get(0).getPassword())) {
                    throw new EtAuthException("Invalid email and/or password.");
                } else {
                    return users.get(0);
                }
            }
        } catch (EmptyResultDataAccessException ignored) {
            throw new EtAuthException("Invalid email and/or password.");
        }

        throw new EtAuthException("An error occurred while finding the user associated with that email.");
    }

    @Override
    public Boolean emailExists(String email) {
        // TODO: fix 'queryForObject(java.lang.String, java.lang.Object[], java.lang.Class<T>)' deprecation
        final Integer user = jdbcTemplate.queryForObject(
                SQL_COUNT_USER_EMAIL,
                new Object[]{email},
                Integer.class
        );

        return user != null ? user > 0 : Boolean.FALSE;
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID, userRowMapper, userId);
    }

    private final RowMapper<User> userRowMapper = ((rs, numOfRows) -> new User(
            rs.getInt("user_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("password")
    ));
}
