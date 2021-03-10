package com.example.marlace.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.example.marlace.Const.USER_API_URL_FORMAT;
import static com.example.marlace.Const.TEST_USER;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String PATH_TO_API_USER = String.format(USER_API_URL_FORMAT, port);

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
