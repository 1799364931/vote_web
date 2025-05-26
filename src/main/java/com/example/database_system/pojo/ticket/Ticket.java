package com.example.database_system.pojo.ticket;


import com.example.database_system.pojo.ticket.TicketLimit;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.UUID;

@Table(name = "tb_ticket")
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

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

    public ArrayList<TicketLimit> getTicketLimit() {
        return ticketLimit;
    }

    public void setTicketLimit(ArrayList<TicketLimit> ticketLimit) {
        this.ticketLimit = ticketLimit;
    }

    @Column(name = "description")
    private String description;
    @Column(name = "weight")
    private Integer weight;

    @OneToMany(mappedBy = "ticket" , cascade = CascadeType.ALL)
    private ArrayList<TicketLimit> ticketLimit;
}
