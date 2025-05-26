package com.example.database_system.pojo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtils jwtUtils;

    //拦截器 拦截过期token
    @NonNull
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull Object handler) throws IOException {
        //放行非token请求
        String path = request.getRequestURI();
        if (path.startsWith("/login") || path.startsWith("/register") || path.startsWith("/public")) {
            return true; // 直接放行
        }

        String token = request.getHeader("Authorization");
        String userAccount = jwtUtils.getAccountFromToken(token);

        if (!token.equals(TokenStorage.getToken(userAccount))) {
            response.sendRedirect("/login"); // Token 无效，跳转到登录页
            return false;
        }

        return true;
    }
}
