package io.papayankey.taskman.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.papayankey.taskman.user.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTUtil {
    public static final String JWT_PREFIX = "Bearer ";
    public static final String JWT_AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_SECRET = "super_jwt_secret";
    public static final Date JWT_EXPIRATION = Date.from(Instant.now().plus(15, ChronoUnit.MINUTES));
    public static final Date JWT_ISSUED = Date.from(Instant.now());

    public String createToken(UserLoginRequest userLoginRequest) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.create()
                .withSubject(userLoginRequest.getUsername())
                .withIssuedAt(JWT_ISSUED)
                .withExpiresAt(JWT_EXPIRATION)
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return new JWT().decodeJwt(token).getSubject();
    }

    public String parseJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(JWT_AUTHORIZATION_HEADER);
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            return authorizationHeader.substring(JWT_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException exception) {
            return false;
        }
        return true;
    }
}
