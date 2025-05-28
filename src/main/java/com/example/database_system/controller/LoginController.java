package com.example.database_system.controller;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.JwtUtils;
import com.example.database_system.pojo.TokenStorage;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.response.UserServiceResponse;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import com.example.database_system.service.User.LoginRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    LoginRegisterUserService loginRegisterUserService;

    @PostMapping("/api/login")
    public ResponseMessage<LoginRegisterUserDto> Login(@RequestBody LoginRegisterUserDto loginRegisterUserDto){
        return loginRegisterUserService.LoginUser(loginRegisterUserDto);
    }

}
