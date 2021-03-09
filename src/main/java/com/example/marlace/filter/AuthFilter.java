package com.example.marlace.filter;


import com.example.marlace.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            final String[] authHeaders = authHeader.split("Bearer");

            if (authHeaders.length > 1 && authHeaders[1] != null) {
                final String token = authHeaders[1];

                try {
                    final Claims claims = Jwts.parser()
                            .setSigningKey(Constants.API_SECRET_KEY)
                            .parseClaimsJws(token)
                            .getBody();
                    final String userIdFromRequest = claims.get("userId").toString();
                    final int userId = Integer.parseInt(userIdFromRequest);
                    request.setAttribute("userId", userId);
                } catch (Exception ignored) {
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid or expired token.");
                    return;
                }
            } else {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be \"Bearer [token]\"");
                return;
            }
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must provided: \"Bearer [token]\"");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
