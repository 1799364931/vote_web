package com.example.database_system.pojo.response;

import com.example.database_system.pojo.user.User;

public class UserServiceResponse {

    public enum ResponseCode{
        USER_NOT_EXIST,
        USER_EXIST,
        LOGIN_PASSWORD_ERROR,
        LOGIN_PASSWORD_CORRECT,
        LOGIN_FAIL,
        LOGIN_SUCCESS,
        REGISTER_FAIL,
        REGISTER_SUCCESS,
        REMOVE_FAIL,
        REMOVE_SUCCESS,
        EDIT_SUCCESS,
    }

    private String message;
    private ResponseCode responseCode;
    private User user;

    public UserServiceResponse(String message,ResponseCode responseCode,User user){
        this.message = message;
        this.responseCode = responseCode;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
