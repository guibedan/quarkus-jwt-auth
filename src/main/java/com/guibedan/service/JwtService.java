package com.guibedan.service;

import com.guibedan.entity.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;

@ApplicationScoped
public class JwtService {

    public String generateToken(User user, long expiresIn) {
        var now = Instant.now();
        return Jwt.issuer("quarkus-jwt-auth")
                .subject(user.userId.toString())
                .upn(user.username)
//                .groups(user.role.toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .sign();
    }

}
