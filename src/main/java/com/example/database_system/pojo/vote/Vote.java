package com.example.database_system.pojo.vote;

import com.example.database_system.pojo.record.VoteRecord;
import com.example.database_system.pojo.ticket.TicketLimit;
import com.example.database_system.pojo.record.VoteDefineRecord;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.vote.option.VoteOption;
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
    @Column(name = "id",nullable = false)
    private UUID id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "start_time",nullable = false)
    private Timestamp startTime;
    @Column(name = "end_time",nullable = false)
    private Timestamp endTime;

    @Column(name = "is_delete",nullable = false)
    private Boolean delete = false;

    @ManyToOne
    @JoinColumn(name = "creator_id",nullable = false)
    private User creatorId;

    @OneToMany(mappedBy = "vote")
    private List<VoteDefineRecord> voteDefineRecords = new ArrayList<>();

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL)
    private List<VoteOption> voteOptions = new ArrayList<>();

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL)
    private List<TicketLimit> ticketLimits = new ArrayList<>();

    @OneToMany(mappedBy = "vote", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VoteRecord> voteRecords = new ArrayList<>();


    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public List<VoteRecord> getVoteRecords() {
        return voteRecords;
    }

    public void setVoteRecords(List<VoteRecord> voteRecords) {
        this.voteRecords = voteRecords;
    }

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

    public List<VoteDefineRecord> getVoteDefineLogs() {
        return voteDefineRecords;
    }

    public void setVoteDefineLogs(List<VoteDefineRecord> voteDefineRecords) {
        this.voteDefineRecords = voteDefineRecords;
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
