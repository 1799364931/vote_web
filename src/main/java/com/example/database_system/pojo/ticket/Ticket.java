package com.example.database_system.pojo.ticket;


import com.example.database_system.pojo.ticket.TicketLimit;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "tb_ticket")
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Integer weight;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketLimit> ticketLimit;

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

    public List<TicketLimit> getTicketLimit() {
        return ticketLimit;
    }

    public void setTicketLimit(List<TicketLimit> ticketLimit) {
        this.ticketLimit = ticketLimit;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
