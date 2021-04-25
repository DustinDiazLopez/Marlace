package com.example.marlace.utilities;

import com.example.marlace.models.EmbeddedEntityMetadata;
import com.example.marlace.models.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public final class Constants {

    public static final String APPLICATION_JSON = "application/json";

    public static final String[] URL_PATTERNS = new String[]{
            "/auth",
            "/api/u",
            "/api/u/delete",
            "/api/u/update",
            "/api/u/projects",
            "/api/project/*",
    };

    public static final class ColumnDefinitions {
        public static final String TEXT = "TEXT";
        public static final String ON_CREATE_TIMESTAMP = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
        public static final String ON_UPDATE_TIMESTAMP = (ON_CREATE_TIMESTAMP + " ON UPDATE CURRENT_TIMESTAMP");
    }

    public static final class JWT {
        public static final String JWT_SECRET_KEY = "(Vo?gKV@qwQ\"HKi4;A)&{%Ftw'u-vgY\\(5";
        public static final long TOKEN_DURATION = 1000 * 60 * 60 * 24; // 1 day
    }

    public static final class Patterns {
        public static final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
    }

    public static final class TestData {
        public static User testUser() {
            final User user = new User();
            user.setFirstName("Dustin");
            user.setLastName("Díaz");
            user.setEmail("hi.dustin.diaz@gmail.com");
            user.setPassword(Utils.hashPassword("hello_password"));
            user.setDescription("This is Dustin's description");
            EmbeddedEntityMetadata metadata = new EmbeddedEntityMetadata();
            metadata.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().minusDays(50)));
            metadata.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            user.setEmbeddedEntityMetadata(metadata);
            return user;
        }
    }
}
