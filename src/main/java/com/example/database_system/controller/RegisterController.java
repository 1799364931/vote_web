package com.example.database_system.controller;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.response.UserServiceResponse;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import com.example.database_system.service.User.LoginRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    LoginRegisterUserService loginRegisterUserService;

    @PostMapping
    public ResponseMessage<LoginRegisterUserDto> Register(@RequestBody LoginRegisterUserDto loginRegisterUserDto){
        return loginRegisterUserService.RegisterUser(loginRegisterUserDto);
    }

}
