package com.example.database_system.pojo.record;

import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.vote.Vote;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_vote_record")
public class VoteRecord {
    @EmbeddedId
    private VoteRecordId voteId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @MapsId("voteId")
    @JoinColumn(name = "vote_id",nullable = false)
    private Vote vote;

    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id",nullable = false)
    private Ticket ticket;

    @Column(name = "vote_count",nullable = false)
    private Integer voteCount;

    public VoteRecordId getVoteId() {
        return voteId;
    }

    public void setVoteId(VoteRecordId voteId) {
        this.voteId = voteId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public void addVoteCount(Integer count) {
        if (this.voteCount == null) {
            this.voteCount = 0;
        }
        this.voteCount += count;
    }
}


