package com.example.database_system.service.User;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.util.JwtUtils;
import com.example.database_system.pojo.util.TokenStorage;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.dto.user.LoginRegisterUserDto;
import com.example.database_system.pojo.user.User;
import com.example.database_system.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginRegisterUserService {

    private final UserRepository userRepository;
    private final  SecurityConfig securityConfig;
    private final  JwtUtils jwtUtils;

    @Autowired
    public LoginRegisterUserService(UserRepository userRepository, SecurityConfig securityConfig, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public ResponseMessage<LoginRegisterUserDto> RegisterUser(LoginRegisterUserDto loginRegisterUserDto) {
        var user = new User();
        user.setAccount(loginRegisterUserDto.getAccount());
        user.setPassword(securityConfig.passwordEncoder().encode(loginRegisterUserDto.getPassword()));
        user.setRole(loginRegisterUserDto.getRole());
        user.setName(user.getAccount());
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
