package com.example.database_system.pojo.dto.user;

import com.example.database_system.pojo.user.Role;
import com.example.database_system.pojo.user.User;

public class UserDto {
    private String account;
    private String name;
    private Role role;

    public UserDto() {
    }

    public UserDto(User user) {
        this.account = user.getAccount();
        this.name = user.getName();
        this.role = user.getRole();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
