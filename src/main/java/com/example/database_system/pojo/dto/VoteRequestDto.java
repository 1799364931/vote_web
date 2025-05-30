package com.example.database_system.pojo.dto;

import java.util.List;
import java.util.UUID;

public class VoteRequestDto {
    public static class TicketInfo{
        public UUID ticketId;
        public Integer count;
    }

    private UUID voteOptionId;
    private List<TicketInfo> ticketInfoList;

    public UUID getVoteOptionId() {
        return voteOptionId;
    }

    public void setVoteOptionId(UUID voteOptionId) {
        this.voteOptionId = voteOptionId;
    }

    public List<TicketInfo> getTicketInfoList() {
        return ticketInfoList;
    }

    public void setTicketInfoList(List<TicketInfo> ticketInfoList) {
        this.ticketInfoList = ticketInfoList;
    }
}
