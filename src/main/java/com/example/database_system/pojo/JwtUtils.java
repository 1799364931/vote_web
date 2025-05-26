package com.example.database_system.pojo;

import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {
    private final String secret = Base64.getEncoder().encodeToString(new byte[32]);

    // 生成 Token
    public String generateToken(LoginRegisterUserDto userDto) {
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("account",userDto.getAccount())
                .claim("role",userDto.getRole())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24L))
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

    // 从 Token 中获取用户名
    public String getAccountFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("account",String.class);
    }

    // 从 Token 中获取用户名
    public String getRoleFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("role",String.class);
    }


}