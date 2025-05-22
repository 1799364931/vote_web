package com.example.database_system.service;

import com.example.database_system.config.SecurityConfig;
import com.example.database_system.pojo.UserServiceResponse;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import com.example.database_system.pojo.user.User;
import com.example.database_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginRegisterUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    public UserServiceResponse RegisterUser(LoginRegisterUserDto loginRegisterUserDto){
        var user = new User();
        user.setAccount(loginRegisterUserDto.getAccount());
        user.setPassword(securityConfig.passwordEncoder().encode(loginRegisterUserDto.getPassword()));
        user.setRole(loginRegisterUserDto.getRole());
        if(userRepository.findByaccount(user.getAccount()).isPresent()){
            //账户名冲突
            return new UserServiceResponse("", UserServiceResponse.ResponseCode.REGISTER_FAIL,user);
        }
        else{
            userRepository.save(user);
            return new UserServiceResponse("", UserServiceResponse.ResponseCode.REGISTER_SUCCESS,user);
        }
    }

    public UserServiceResponse LoginUser(LoginRegisterUserDto loginRegisterUserDto){
        var user = new User();
        user.setAccount(loginRegisterUserDto.getAccount());
        user.setPassword(securityConfig.passwordEncoder().encode(loginRegisterUserDto.getPassword()));
        user.setRole(loginRegisterUserDto.getRole());
        var savedUser = userRepository.findByaccount(user.getAccount());
        if(savedUser.isPresent()){
            System.out.println(user.getPassword());
            if(securityConfig.passwordEncoder().matches(loginRegisterUserDto.getPassword(),savedUser.get().getPassword())){
                return new UserServiceResponse("", UserServiceResponse.ResponseCode.LOGIN_PASSWORD_CORRECT,user);
            }
            else{
                return new UserServiceResponse("", UserServiceResponse.ResponseCode.LOGIN_PASSWORD_ERROR,user);
            }
        }
        else{
            return new UserServiceResponse("", UserServiceResponse.ResponseCode.USER_NOT_EXIST,user);
        }
    }



}
