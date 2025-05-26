package com.example.database_system.controller;

import com.example.database_system.pojo.TokenStorage;
import com.example.database_system.pojo.response.UserServiceResponse;
import com.example.database_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/edit")
public class UserEditController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("{userId}")
    public UserServiceResponse EditUser(@PathVariable UUID userId, @RequestHeader("Authorization") String authorizationHeader){
        var user = userRepository.findById(userId);
        String token = authorizationHeader.replace("Bearer ", "");
        if(user.isPresent()){
            // 这里可以添加编辑用户的逻辑
            // 例如修改用户信息等

            return new UserServiceResponse("User edited successfully", UserServiceResponse.ResponseCode.EDIT_SUCCESS, user.get());
        } else {
            return new UserServiceResponse("User not found", UserServiceResponse.ResponseCode.USER_NOT_EXIST, null);
        }
    }
}
