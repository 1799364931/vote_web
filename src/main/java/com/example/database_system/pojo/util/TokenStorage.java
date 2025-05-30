package com.example.database_system.pojo.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStorage {
    private static final ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    public static void storeToken(String userId, String token) {
        tokenMap.put(userId, token); // 存储用户的 Token
    }

    public static String getToken(String userId) {
        return tokenMap.get(userId); // 获取 Token
    }

    public static void removeToken(String userId) {
        tokenMap.remove(userId); // 用户登出时删除 Token
    }
}
