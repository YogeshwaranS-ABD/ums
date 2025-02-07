package com.i2i.ums.utils;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import com.i2i.ums.exception.UnAuthorizedException;

public class JwtUtil {
    private static final String secretKey;
    private static final SecretKey key;

    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            key = Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new JwtException("Error in generating key", e);
        }
    }

    public static String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 2 * 3600 * 1000))
                .and()
                .signWith(key)
                .compact();
    }

    public static void validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUserName(token);
            if (!username.equalsIgnoreCase(userDetails.getUsername())) {
                throw new UnAuthorizedException("Token Validation failed: This token is not authenticated with your username");
            } else if (isTokenExpired(token)){
                throw new UnAuthorizedException("Token Expired, Login again");
            }
        } catch (Exception e) {
            throw new UnAuthorizedException("Error in validating the token, invalid token\n" + e.getMessage());
        }
    }

    public static String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            throw new UnAuthorizedException("Error in extracting expiration from token");
        }
    }

    public static Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new UnAuthorizedException("Invalid token or Token Expired");
        }
    }
}