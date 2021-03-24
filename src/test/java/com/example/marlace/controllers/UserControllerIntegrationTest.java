package com.example.marlace.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.example.marlace.Constants.Server.USER_URL;

@SpringBootTest
public class UserControllerIntegrationTest {

    private final String PATH_TO_API_USER = String.format(USER_URL, 8080);

    @Test
    public void testLogin() {

    }

    @Test
    @DisplayName("register-user-test")
    public void testRegister() throws IOException, JSONException {
        JSONObject object = new JSONObject();
        object.put("firstName", "Dustin");
        object.put("lastName", "Diaz");
        object.put("email", "example@gmail.com");
        object.put("password", "hello_password");
        final String body = object.toString();
    }

}
