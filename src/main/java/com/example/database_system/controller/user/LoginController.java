package com.example.database_system.controller.user;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.dto.user.LoginRegisterUserDto;
import com.example.database_system.pojo.util.JwtUtils;
import com.example.database_system.pojo.util.TokenStorage;
import com.example.database_system.service.User.LoginRegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/login")
@Tag(name = "用户登录", description = "用户登录相关接口")
public class LoginController {

    private final LoginRegisterUserService loginRegisterUserService;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginController(LoginRegisterUserService loginRegisterUserService,JwtUtils jwtUtils) {
        this.loginRegisterUserService = loginRegisterUserService;
        this.jwtUtils = jwtUtils;

    }

    @PostMapping("/api/login")
    @Operation(summary = "用户登录", description = "用户通过用户名和密码进行登录，返回用户信息和JWT令牌")
    public ResponseMessage<LoginRegisterUserDto> Login(@RequestBody LoginRegisterUserDto loginRegisterUserDto){
        return loginRegisterUserService.LoginUser(loginRegisterUserDto);
    }

    @PostMapping("/api/logout")
    @Operation(summary = "用户登出", description = "用户登出，服务器移除用户JWT")
    public ResponseMessage<String> Logout(HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.validateToken(token)? jwtUtils.getIdFromToken(token) : null;
        if (userId != null) {
            TokenStorage.removeToken(userId.toString());
            return ResponseMessage.success("logout success","success");
        }
        return ResponseMessage.error("logout fail","fail", HttpStatus.BAD_REQUEST.value());
    }
}
