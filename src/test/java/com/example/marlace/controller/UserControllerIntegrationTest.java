package com.example.marlace.controller;
import com.example.marlace.model.User;
import com.example.marlace.mappers.UserRowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.example.marlace.Const.USER_API_URL_FORMAT;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String PATH_TO_API_USER = String.format(USER_API_URL_FORMAT, port);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("find-all-users-test")
    void findAllUsersTest() {
        String sql = "SELECT * FROM `users`";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        assertEquals(users.size(), 1);
        users.forEach(System.err::println); // just to print  in red color
    }

    @Test
    public void testLogin() {

    }

    @Test
    public void testRegister() {

//        System.err.println(PATH_TO_API_USER + "/register");
//        ResponseEntity<String> responseEntity = this.restTemplate
//                .postForEntity(
//                PATH_TO_API_USER + "/register",
//                    TEST_USER,
//                    String.class
//                );
//        System.err.println(responseEntity);
//        assertEquals(201, responseEntity.getStatusCodeValue());
    }

}
