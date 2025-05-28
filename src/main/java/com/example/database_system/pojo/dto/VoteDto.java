package com.example.database_system.pojo.dto;

import com.example.database_system.pojo.vote.Vote;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.sql.Timestamp;
import java.util.UUID;

public class VoteDto {

    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private Timestamp startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private Timestamp endTime;
    private Boolean isTimeout;


    public VoteDto() {

    }

    public VoteDto(Vote vote) {
        this.title = vote.getTitle();
        this.description = vote.getDescription();
        this.startTime = vote.getStartTime();
        this.endTime = vote.getEndTime();
        this.isTimeout = this.endTime.before(new Timestamp(System.currentTimeMillis()));
    }

    public VoteDto(String title, String description, Timestamp startTime, Timestamp endTime, Boolean isTimeout) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isTimeout = isTimeout;
    }

    public Boolean getTimeout() {
        return isTimeout;
    }

    public void setTimeout(Boolean timeout) {
        isTimeout = timeout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "VoteDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isTimeout=" + isTimeout +
                '}';
    }
}
