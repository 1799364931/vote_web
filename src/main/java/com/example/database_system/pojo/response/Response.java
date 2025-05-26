package com.example.database_system.pojo.response;

public class Response {
    private String message;
    private UserServiceResponse.ResponseCode responseCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserServiceResponse.ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(UserServiceResponse.ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
