package com.example.marlace;

import com.example.marlace.model.User;

public final class Constants {

    public static final class Server {
        public static final String URL = "http://localhost:%d/api";
        public static final String USER_URL = URL + "/u";
    }

    public static final class TestObjects {


        public static final User TEST_USER = new User(
                1,
                "Example",
                "User",
                "email@example.com",
                "Rzxf9'N[fXbUM9]=d$dsyb&.W<-;'NT{"
        );
    }

}
