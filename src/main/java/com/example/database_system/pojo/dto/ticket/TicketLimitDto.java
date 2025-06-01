package com.example.database_system.pojo.dto.ticket;

import com.example.database_system.pojo.ticket.TicketLimit;

import java.util.UUID;

public class TicketLimitDto {
    private UUID ticketId;
    private String description;
    private Integer voteCount;

    public TicketLimitDto() {

    }

    public TicketLimitDto(TicketLimit ticketLimit) {
        this.ticketId = ticketLimit.getTicket().getId();
        this.description = ticketLimit.getTicket().getDescription();
        this.voteCount = ticketLimit.getCount();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
