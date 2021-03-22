package com.example.marlace.repository;

import com.example.marlace.exceptions.MarlaceBadRequestException;
import com.example.marlace.exceptions.MarlaceResourceNotFoundException;
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

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

//    @Autowired
//    private SessionFactory sessionFactory;

    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT c.`category_id`, c.`user_id`, c.`title`, c.`description`, coalesce(sum(t.`amount`), 0) as `total_expense` \n" +
            "\tFROM `categories` c INNER JOIN `transactions` t \n" +
            "\tON c.`category_id` = t.`category_id` \n" +
            "\tWHERE c.`user_id` = ? AND c.`category_id` = ? \n" +
            "\tGROUP BY c.`category_id`";
    private static final String SQL_CREATE_CATEGORY = "INSERT INTO `categories`(`user_id`, `title`, `description`) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_CATEGORY_BY_ID_FALLBACK = "SELECT * FROM `categories` WHERE user_id = ? AND category_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) throws MarlaceResourceNotFoundException {
        return null;
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws MarlaceResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_CATEGORY_BY_ID,
                    new Object[]{userId, categoryId},
                    categoryRowMapper
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarlaceResourceNotFoundException("The specified category was not found.");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws MarlaceBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_CATEGORY, Statement.RETURN_GENERATED_KEYS);
                int index = 0;
                ps.setInt(++index, userId);
                ps.setString(++index, title);
                ps.setString(++index, description);
                System.out.println(ps);

                return ps;
            }, keyHolder);

            final String generatedKey = keyHolder.getKeys().get("GENERATED_KEY").toString();
            return Integer.parseInt(generatedKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarlaceBadRequestException("Invalid request. Failed to create category.");
        }
    }

    @Override
    public Integer createCategory(Integer userId, String title, String description) throws MarlaceBadRequestException {
//        Session session;
//
//        try {
//            session = sessionFactory.openSession();
//            session.beginTransaction();
//            final Integer id = (Integer) session.save(new Category());
//            System.err.println("Category ID: " + id);
//            session.getTransaction().commit();
//            return id;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws MarlaceBadRequestException {

    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {

    }

    private final RowMapper<Category> categoryRowMapper = ((rs, numOfRows) -> new Category(
            rs.getInt("category_id"),
            rs.getInt("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getDouble("total_expense")
    ));

    private final RowMapper<Category> categoryRowMapperNoExpense = ((rs, numOfRows) -> new Category(
            rs.getInt("category_id"),
            rs.getInt("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            0d
    ));
}
