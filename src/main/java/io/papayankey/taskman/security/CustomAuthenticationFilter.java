package io.papayankey.taskman.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.papayankey.taskman.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static io.papayankey.taskman.security.constant.Constant.*;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/auth/login") || path.equals("/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = parseJWT(request);
        if (token != null && isNotAuthenticated()) {
            if (validateToken(token)) {
                String username = extractUsername(token);
                if (username != null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private String extractUsername(String token) {
        return new JWT().decodeJwt(token).getSubject();
    }

    private String parseJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(JWT_AUTHORIZATION_HEADER);
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            return authorizationHeader.substring(JWT_PREFIX.length());
        }
        return null;
    }

    private boolean validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

}
