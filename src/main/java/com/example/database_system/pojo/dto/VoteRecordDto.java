package com.example.database_system.pojo.dto;

import com.example.database_system.pojo.dto.ticket.TicketDto;

import java.sql.Timestamp;
import java.util.UUID;

public class VoteRecordDto {
    private UUID voteId;
    private String voteTitle;
    private VoteOptionDto voteOption;
    private TicketDto ticket;
    private Timestamp voteTime;

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public UUID getVoteId() {
        return voteId;
    }

    public void setVoteId(UUID voteId) {
        this.voteId = voteId;
    }

    public VoteOptionDto getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOptionDto voteOption) {
        this.voteOption = voteOption;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    public Timestamp getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Timestamp voteTime) {
        this.voteTime = voteTime;
    }
}
