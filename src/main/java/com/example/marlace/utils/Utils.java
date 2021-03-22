package com.example.marlace.utils;

import com.example.marlace.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static String hashPassword(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public static Map<String, String> generateJWTToken(final User user) {
        final long timestamp = System.currentTimeMillis();
        final String token = Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS512, Constants.JWT.JWT_SECRET_KEY)
                .setIssuedAt(new Date(timestamp + Constants.JWT.TOKEN_DURATION))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        final Map<String, String> map = new HashMap<>(1);
        map.put("token", token);
        return map;
    }
}
