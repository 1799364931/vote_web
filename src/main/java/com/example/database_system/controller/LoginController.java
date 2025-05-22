package com.example.database_system.controller;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.ResponseMessage;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.UserServiceResponse;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import com.example.database_system.service.LoginRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    LoginRegisterUserService loginRegisterUserService;

//    @GetMapping
//    public String ShowLoginPage() {
//        return "index"; // 返回你自己的 login.html
//    }

    @GetMapping
    public ResponseMessage<LoginRegisterUserDto> Login(@RequestBody LoginRegisterUserDto loginRegisterUserDto){

        var response = loginRegisterUserService.LoginUser(loginRegisterUserDto);

        if(response.getResponseCode() == UserServiceResponse.ResponseCode.LOGIN_PASSWORD_ERROR ||
            response.getResponseCode() == UserServiceResponse.ResponseCode.USER_NOT_EXIST ){
            return ResponseMessage.fail(loginRegisterUserDto,"login fail ");
        }
        else{
            return ResponseMessage.success(loginRegisterUserDto,"login success.");
        }
    }

}
