package com.example.database_system.pojo.vote.option;

import com.example.database_system.pojo.record.VoteOptionRecord;
import com.example.database_system.pojo.vote.Vote;
import jakarta.persistence.*;

import java.util.List;
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
    @Column(name = "position")
    private Integer position;
    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    //一个选项可以有多个资源，例如图片、音频等
    @OneToMany(mappedBy = "voteOption", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OptionResource> optionResource;

    @OneToMany(mappedBy = "voteOption", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VoteOptionRecord> voteOptionRecords;

    public VoteOption() {
        voteCount = 0;
    }

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void addVoteCount(Integer count) {
        if (count == null || count < 0) {
            throw new IllegalArgumentException("投票数不能为负数");
        }
        this.voteCount += count;
    }

    public List<OptionResource> getOptionResource() {
        return optionResource;
    }

    public void setOptionResource(List<OptionResource> optionResource) {
        this.optionResource = optionResource;
    }

    public List<VoteOptionRecord> getVoteOptionRecords() {
        return voteOptionRecords;
    }

    public void setVoteOptionRecords(List<VoteOptionRecord> voteOptionRecords) {
        this.voteOptionRecords = voteOptionRecords;
    }
}
