package com.example.database_system.pojo.util;

import com.example.database_system.pojo.user.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {
    private final String secret = Base64.getEncoder().encodeToString(new byte[32]);

    // 生成 Token
    public String generateToken(User user) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("account", user.getAccount())
                .claim("id", user.getId().toString())
                .claim("role", user.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24L))
                .setId(UUID.randomUUID().toString())
                //签名
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();

    }


    // 验证 Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAccountFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("account", String.class);
    }

    public String getRoleFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public UUID getIdFromToken(String token) {
        return UUID.fromString(Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("id", String.class));
    }


}