package com.example.database_system.pojo;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Table(name = "tb_vote")
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creatorId;

    @OneToMany(mappedBy = "vote")
    private ArrayList<VoteDefineLog> voteDefineLogs;

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL)
    private ArrayList<VoteOption> voteOptions;

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL)
    private ArrayList<TicketLimit> ticketLimits;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public User getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(User creatorId) {
        this.creatorId = creatorId;
    }

    public ArrayList<VoteDefineLog> getVoteDefineLogs() {
        return voteDefineLogs;
    }

    public void setVoteDefineLogs(ArrayList<VoteDefineLog> voteDefineLogs) {
        this.voteDefineLogs = voteDefineLogs;
    }

    public ArrayList<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(ArrayList<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public ArrayList<TicketLimit> getTicketLimits() {
        return ticketLimits;
    }

    public void setTicketLimits(ArrayList<TicketLimit> ticketLimits) {
        this.ticketLimits = ticketLimits;
    }
}
