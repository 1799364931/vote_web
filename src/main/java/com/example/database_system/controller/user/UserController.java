package com.example.database_system.controller.user;

import com.example.database_system.pojo.dto.user.UserDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.util.JwtUtils;
import com.example.database_system.service.User.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("user")
@Tag(name = "用户相关接口", description = "用于获取用户信息，修改用户信息等")
public class UserController {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Autowired
    public UserController(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @GetMapping
    public ResponseMessage<UserDto> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.validateToken(token)? jwtUtils.getIdFromToken(token) : null;
        return userService.getUserInfo(userId);
    }
}
