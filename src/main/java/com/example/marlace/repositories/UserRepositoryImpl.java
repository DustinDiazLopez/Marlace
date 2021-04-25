package com.example.marlace.repositories;

import com.example.marlace.exceptions.MarlaceAuthException;
import com.example.marlace.models.User;
import com.example.marlace.repositories.mappers.UserRowMapper;
import com.example.marlace.utilities.HibernateWrapper;
import com.example.marlace.utilities.Utils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM `user` WHERE `email` = ?";
    private static final String HQL_FIND_USER_BY_EMAIL = "from User u where u.email = :email ";

    private final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws MarlaceAuthException {
        final String hashedPassword = Utils.hashPassword(password);
        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        return (Integer) HibernateWrapper.saveOne(sessionFactory, user);
    }

    @Override
    public Boolean delete(final Integer id) {
        final User user = new User();
        user.setUserId(id);
        return HibernateWrapper.delete(sessionFactory, user);
    }

    @Override
    public Boolean update(final User user) throws MarlaceAuthException {
        return HibernateWrapper.saveOrUpdate(sessionFactory, user);
    }

    @Override
    public User findById(final Integer userId) {
        return HibernateWrapper.get(sessionFactory, User.class, userId).get(0);
    }

    @Override
    public User findByEmail(final String email) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("email", email);
        return (User) HibernateWrapper.queryList(sessionFactory, HQL_FIND_USER_BY_EMAIL, params).get(0);
    }

    @Override
    public User authenticate(final String email, final String password) throws MarlaceAuthException {
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
    public Boolean emailExists(final String email) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("email", email);
        return HibernateWrapper.queryList(sessionFactory, HQL_FIND_USER_BY_EMAIL, params).size() > 0;
    }
}
