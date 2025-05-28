package com.example.database_system.pojo.dto;


import com.example.database_system.pojo.vote.VoteOption;

import java.util.UUID;

public class VoteOptionDto {

    private String description;
    private Integer position;
    private Integer voteCount;

    public VoteOptionDto() {
    }

    public VoteOptionDto(VoteOption voteOption) {
        this.description = voteOption.getDescription();
        this.position = voteOption.getPosition();
        this.voteCount = voteOption.getVoteCount();
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

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

    @Override
    public String toString() {
        return "VoteOptionDto{" +
                "description='" + description + '\'' +
                ", position=" + position +
                ", voteCount=" + voteCount +
                '}';
    }
}
