package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.model.User;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtService {
    String extractEmailAddress(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsSolver);

    String generateToken(User user);

    ErrorCode validateToken(String token, User user);

    int getExpireIn();


}
