package com.example.database_system.pojo.record;


import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.vote.Vote;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Table (name = "tb_vote_define_manager")
@Entity
public class VoteDefineLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "operation")
    private String operation;
    @Column(name = "time")
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User operator;
    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    public VoteDefineLog(String operation, Timestamp time, User operator, Vote vote) {
        this.operation = operation;
        this.time = time;
        this.operator = operator;
        this.vote = vote;
    }

    public VoteDefineLog() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
