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
    public static boolean matchesEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Returns the inputted {@link java.lang.String} if the {@link java.lang.String} is not <b>null</b> nor {@link java.lang.String#isEmpty()} (after a {@link java.lang.String#trim()})
     *
     * @param str the {@link java.lang.String} to be checked (and trimmed).
     * @return <b>null</b> if the inputted {@link java.lang.String} is <b>null</b> or if the {@link java.lang.String} after a {@link java.lang.String#trim()} is {@link java.lang.String#isEmpty()}
     * else it will return the actual inputted {@link java.lang.String} with the applied {@link java.lang.String#trim()}
     */
    public static String nullOrEmpty(String str) {
        if (str == null) {
            return null;
        } else {
            str = str.trim();
            return str.isEmpty() ? null : str;
        }
    }

    /**
     * Wrapper for {@link #nullOrEmpty(String)}, instead of returning the {@link java.lang.String} it checks to see weather the function returns <b>null</b> (which means that the inputted {@link java.lang.String} is <b>null</b> or empty)
     *
     * @param str the {@link java.lang.String} to check
     * @return <b>true</b> if the {@link java.lang.String} is <b>null</b> or {@link java.lang.String#isEmpty()} (after a {@link java.lang.String#trim()})
     * @see #nullOrEmpty(String)
     */
    public static boolean isNullOrEmpty(String str) {
        return nullOrEmpty(str) == null;
    }

    public static String censorEmail(final String email) {
        return email.charAt(0) + "***" + email.substring(email.indexOf('@') - 1);
    }
}
