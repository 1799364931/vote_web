package com.example.database_system.controller.user;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.dto.user.LoginRegisterUserDto;
import com.example.database_system.service.User.LoginRegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "用户登录", description = "用户登录相关接口")
public class LoginController {

    private final LoginRegisterUserService loginRegisterUserService;

    @Autowired
    public LoginController(LoginRegisterUserService loginRegisterUserService, SecurityConfig securityConfig) {
        this.loginRegisterUserService = loginRegisterUserService;
    }

    @PostMapping("/api/login")
    @Operation(summary = "用户登录", description = "用户通过用户名和密码进行登录，返回用户信息和JWT令牌")
    public ResponseMessage<LoginRegisterUserDto> Login(@RequestBody LoginRegisterUserDto loginRegisterUserDto){
        return loginRegisterUserService.LoginUser(loginRegisterUserDto);
    }

}
