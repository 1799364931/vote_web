package com.example.database_system.service.User;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.JwtUtils;
import com.example.database_system.pojo.TokenStorage;
import com.example.database_system.pojo.dto.UserDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.response.UserServiceResponse;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import com.example.database_system.pojo.user.User;
import com.example.database_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginRegisterUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseMessage<LoginRegisterUserDto> RegisterUser(LoginRegisterUserDto loginRegisterUserDto) {
        var user = new User();
        user.setAccount(loginRegisterUserDto.getAccount());
        user.setPassword(securityConfig.passwordEncoder().encode(loginRegisterUserDto.getPassword()));
        user.setRole(loginRegisterUserDto.getRole());
        if (userRepository.findByaccount(user.getAccount()).isPresent()) {
            //账户名冲突
            return ResponseMessage.error(loginRegisterUserDto, "账户名已存在，请更换账户名", HttpStatus.BAD_REQUEST.value());
        } else {
            userRepository.save(user);
            return ResponseMessage.success(loginRegisterUserDto, "注册成功，请登录");
        }
    }

    public ResponseMessage<LoginRegisterUserDto> LoginUser(LoginRegisterUserDto loginRegisterUserDto) {
        var user = new User();
        user.setAccount(loginRegisterUserDto.getAccount());
        user.setPassword(securityConfig.passwordEncoder().encode(loginRegisterUserDto.getPassword()));
        user.setRole(loginRegisterUserDto.getRole());
        var savedUser = userRepository.findByaccount(user.getAccount());
        if (savedUser.isPresent()) {
            if (securityConfig.passwordEncoder().matches(loginRegisterUserDto.getPassword(), savedUser.get().getPassword())) {
                loginRegisterUserDto.setToken(jwtUtils.generateToken(savedUser.get()));
                TokenStorage.storeToken(savedUser.get().getAccount(), loginRegisterUserDto.getToken());
                return ResponseMessage.success(loginRegisterUserDto, "登录成功");
            } else {
                return ResponseMessage.error(loginRegisterUserDto, "密码错误", HttpStatus.BAD_REQUEST.value());
            }
        } else {
            return ResponseMessage.error(loginRegisterUserDto, "账户名不存在", HttpStatus.BAD_REQUEST.value());
        }
    }



}
