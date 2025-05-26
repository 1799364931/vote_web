package com.example.database_system.controller;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.JwtUtils;
import com.example.database_system.pojo.TokenStorage;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.response.UserServiceResponse;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import com.example.database_system.service.LoginRegisterUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.Token;
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

    @Autowired
    JwtUtils jwtUtils;


//    @GetMapping
//    public String ShowLoginPage() {
//        return "login"; // 返回你自己的 login.html
//    }

    @PostMapping("/dologin")
    public ResponseMessage<LoginRegisterUserDto> Login(@RequestBody LoginRegisterUserDto loginRegisterUserDto){
        System.out.println(loginRegisterUserDto.getAccount());

        var response = loginRegisterUserService.LoginUser(loginRegisterUserDto);
        if(response.getResponseCode() == UserServiceResponse.ResponseCode.LOGIN_PASSWORD_ERROR ||
            response.getResponseCode() == UserServiceResponse.ResponseCode.USER_NOT_EXIST ){
            return ResponseMessage.fail(loginRegisterUserDto,"login fail ");
        }
        else{
            String token = jwtUtils.generateToken(loginRegisterUserDto);
            //每次登录都 更新token
            TokenStorage.storeToken(loginRegisterUserDto.getAccount(),token);
            return ResponseMessage.loginSuccess(loginRegisterUserDto,"login success.", token);
        }
    }

}
