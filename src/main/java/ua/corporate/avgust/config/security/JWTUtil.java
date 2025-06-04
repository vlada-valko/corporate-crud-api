package ua.corporate.avgust.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secretKey;
    @Value("${jwt_issuer}")
    private String withIssuer;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject("user")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(withIssuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)) // 10 days
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT =
                JWT.require(Algorithm.HMAC256(secretKey))
                        .withSubject("***")
                        .withIssuer("***")
                        .build()
                        .verify(token);
        return decodedJWT.getClaim("username").asString();
    }
}
