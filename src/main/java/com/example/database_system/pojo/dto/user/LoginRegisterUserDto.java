package com.example.database_system.pojo.dto.user;

import com.example.database_system.pojo.user.Role;
import com.example.database_system.pojo.user.User;

public class LoginRegisterUserDto {

    private String account;
    private String password;
    private Role role;
    private String token;

    public LoginRegisterUserDto() {
    }

    public LoginRegisterUserDto(User user) {
        this.account = user.getAccount();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
