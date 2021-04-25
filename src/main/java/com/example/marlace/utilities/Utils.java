package com.example.marlace.utilities;

import com.example.marlace.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.marlace.utilities.Constants.Patterns.EMAIL_PATTERN;

public class Utils {
    /**
     * Hashes the inputted text.
     *
     * @param password the text to hash
     * @return returns the hashed version of the text
     */
    public static String hashPassword(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    /**
     * Determines whether the plaintext password matches the inputted hashed password
     *
     * @param plaintext the password in plaintext
     * @param hashed    the hashed password
     * @return returns whether the plaintext password is the same as the hashed password
     */
    public static boolean checkPassword(final String plaintext, final String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    /**
     * Generates a JWT token based on the inputted user's userId, email, firstName, and lastName.
     *
     * @param user the user
     * @return the token
     */
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

    /**
     * Checks if the inpputed {@link java.lang.String} matches an email pattern
     *
     * @param email the string to check if it matches an email pattern
     * @return returns whether the inputted text matches the email pattern
     * @see Constants.Patterns#EMAIL_PATTERN
     */
    public static boolean matchesEmail(final String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
