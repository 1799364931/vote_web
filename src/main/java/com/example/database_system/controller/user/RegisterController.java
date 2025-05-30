package com.example.database_system.controller.user;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.dto.user.LoginRegisterUserDto;
import com.example.database_system.service.User.LoginRegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@Tag(name = "用户注册", description = "用户注册相关接口")
public class RegisterController {

    private final LoginRegisterUserService loginRegisterUserService;

    @Autowired
    public RegisterController(LoginRegisterUserService loginRegisterUserService, SecurityConfig securityConfig) {
        this.loginRegisterUserService = loginRegisterUserService;
    }

    @PostMapping("/api/register")
    @Operation(summary = "用户注册", description = "用户通过用户名和密码进行注册，返回注册结果")
    public ResponseMessage<LoginRegisterUserDto> Register(@RequestBody LoginRegisterUserDto loginRegisterUserDto){
        return loginRegisterUserService.RegisterUser(loginRegisterUserDto);
    }

}
