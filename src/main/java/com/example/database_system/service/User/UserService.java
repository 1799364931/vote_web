package com.example.database_system.service.User;

import com.example.database_system.pojo.dto.user.UserDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //获取用户信息
    public ResponseMessage<UserDto> getUserInfo(UUID userId) {
        UserDto userDto = userRepository.findById(userId)
                .map(UserDto::new)
                .orElse(null);

        if (userDto == null) {
            return ResponseMessage.error(null,"用户不存在", HttpStatus.NOT_FOUND.value());
        }

        return ResponseMessage.success(userDto,"获取用户信息成功");
    }
}
