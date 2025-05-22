package com.example.database_system.pojo;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "tb_vote_option")
@Entity
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "description")
    private String description;
    @Column(name = "vote_count")
    private Integer voteCount;
    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
