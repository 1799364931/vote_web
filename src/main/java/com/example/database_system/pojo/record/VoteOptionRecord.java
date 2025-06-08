package com.example.database_system.pojo.record;

import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.vote.option.VoteOption;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "tb_vote_option_record",indexes = {
        @Index(name = "idx_user_vote",columnList = "voter_id")
})
@Entity
public class VoteOptionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "voter_id",nullable = false)
    private User voter;

    @ManyToOne
    @JoinColumn(name = "vote_option_id",nullable = false)
    private VoteOption voteOption;

    @ManyToOne
    @JoinColumn(name = "ticket_id",nullable = false)
    private Ticket ticket;

    @Column(name = "time",nullable = false)
    private Timestamp time;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
