package com.example.database_system.pojo.dto;


public class VoteOptionDto {


    private String description;
    private Integer position;


    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
