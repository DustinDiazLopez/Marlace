package com.example.marlace.repository;

import com.example.marlace.exceptions.EtBadRequestException;
import com.example.marlace.exceptions.EtResourceNotFoundException;
import com.example.marlace.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Stack;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT c.`category_id`, c.`user_id`, c.`title`, c.`description`, coalesce(sum(t.`amount`), 0) as `total_expense` \n" +
            "\tFROM `categories` c INNER JOIN `transactions` t \n" +
            "\tON c.`category_id` = t.`category_id` \n" +
            "\tWHERE c.`user_id` = ? AND c.`category_id` = ? \n" +
            "\tGROUP BY c.`category_id`";
    private static final String SQL_CREATE_CATEGORY = "INSERT INTO `categories`(`user_id`, `title`, `description`) VALUES (?, ?, ?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
        return null;
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_CATEGORY_BY_ID,
                    new Object[]{userId, categoryId},
                    categoryRowMapper
            );
        } catch (Exception e) {
            throw new EtResourceNotFoundException("The specified category was not found.");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CATEGORY, Statement.RETURN_GENERATED_KEYS);
                int index = 0;
                ps.setInt(++index, userId);
                ps.setString(++index, title);
                ps.setString(++index, description);
                return ps;
            });

            System.out.println(keyHolder.getKeys());
            System.out.println(keyHolder.getKeys().get("GENERATED_KEY"));
            return (Integer) keyHolder.getKeys().get("GENERATED_KEY");
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request. Failed to create category.");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {

    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {

    }

    private final RowMapper<Category> categoryRowMapper = ((rs, numOfRows) -> new Category(
            rs.getInt("category_id"),
            rs.getInt("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getFloat("total_expense")
    ));
}
