package com.example.database_system.pojo.dto.ticket;

import com.example.database_system.pojo.ticket.Ticket;

import java.util.UUID;

public class TicketDto {
    private UUID ticketId;
    private String description;
    private Integer weight;

    public TicketDto() {
    }

    public TicketDto(UUID ticketId, String description, Integer weight) {
        this.ticketId = ticketId;
        this.description = description;
        this.weight = weight;
    }

    public TicketDto(Ticket ticket) {
        this.ticketId = ticket.getId();
        this.description = ticket.getDescription();
        this.weight = ticket.getWeight();
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
