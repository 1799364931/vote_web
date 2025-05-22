package com.example.database_system.service;

import com.example.database_system.pojo.User;
import com.example.database_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //用户整体的增删改
    public User AddUser(User user){
        userRepository.save(user);
        return user;
    }

    public User RemoveUser(User user){
        userRepository.delete(user);
        return user;
    }



}
