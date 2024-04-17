package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.model.User;
import com.thanhtd.glassstore.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expires-in}")
    private int expiresIn;

    public String extractEmailAddress(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsSolver) {
        final Claims claims = extractAllClaims(token);
        return claimsSolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn * 1000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public ErrorCode validateToken(String token, User user) {
        if (ObjectUtils.isEmpty(user)) return ErrorCode.TOKEN_INVALID;
        String phone = extractEmailAddress(token);
        if (!phone.equals(user.getEmail()) && isTokenExpired(token)) return ErrorCode.TOKEN_INVALID;
        return ErrorCode.TOKEN_VALID;

    }

    @Override
    public int getExpireIn() {
        return expiresIn;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
