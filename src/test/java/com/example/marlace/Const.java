package com.example.marlace;

import com.example.marlace.model.User;

import java.util.UUID;

public class Const {

    public static final String API_URL_FORMAT = "http://localhost:%d/api";
    public static final String USER_API_URL_FORMAT = API_URL_FORMAT + "/u";
    public static final String CATEGORY_API_URL_FORMAT = API_URL_FORMAT + "/categories";
    public static final User TEST_USER = new User(
            1,
            "Example",
            "User",
             "email@example.com",
            "Rzxf9'N[fXbUM9]=d$dsyb&.W<-;'NT{"
    );

}
