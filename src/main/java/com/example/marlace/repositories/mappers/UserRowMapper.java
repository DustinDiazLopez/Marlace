package com.example.marlace.repositories.mappers;

import com.example.marlace.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.getEmbeddedEntityMetadata().setCreatedAt(rs.getTimestamp("createdAt"));
        user.getEmbeddedEntityMetadata().setUpdatedAt(rs.getTimestamp("updatedAt"));
        return user;
    }
}
