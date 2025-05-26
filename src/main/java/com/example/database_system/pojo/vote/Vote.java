package com.example.database_system.pojo.vote;

import com.example.database_system.pojo.ticket.TicketLimit;
import com.example.database_system.pojo.record.VoteDefineLog;
import com.example.database_system.pojo.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
    private List<VoteDefineLog> voteDefineLogs = new ArrayList<>();

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL)
    private List<VoteOption> voteOptions = new ArrayList<>();

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL)
    private List<TicketLimit> ticketLimits = new ArrayList<>();



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

    public List<VoteDefineLog> getVoteDefineLogs() {
        return voteDefineLogs;
    }

    public void setVoteDefineLogs(List<VoteDefineLog> voteDefineLogs) {
        this.voteDefineLogs = voteDefineLogs;
    }

    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public List<TicketLimit> getTicketLimits() {
        return ticketLimits;
    }

    public void setTicketLimits(List<TicketLimit> ticketLimits) {
        this.ticketLimits = ticketLimits;
    }
}
