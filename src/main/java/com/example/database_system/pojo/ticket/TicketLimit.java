package com.example.database_system.pojo.ticket;

import com.example.database_system.pojo.vote.Vote;
import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "tb_vote_limit",indexes = {
        @Index(name = "idx_vote_ticket" , columnList = "vote_id,ticket_id")
})
@Entity
public class TicketLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
