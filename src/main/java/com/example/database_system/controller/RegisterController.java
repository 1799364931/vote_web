package com.example.database_system.controller;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.ResponseMessage;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.UserServiceResponse;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import com.example.database_system.service.LoginRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        var response = loginRegisterUserService.RegisterUser(loginRegisterUserDto);
        if(response.getResponseCode() == UserServiceResponse.ResponseCode.REGISTER_FAIL){
            return ResponseMessage.fail(loginRegisterUserDto,"create fail , account already exist.");
        }
        else{
            return ResponseMessage.success(loginRegisterUserDto,"create success.");
        }
    }

}
