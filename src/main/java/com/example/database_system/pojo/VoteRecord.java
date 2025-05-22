package com.example.database_system.pojo;

import com.example.database_system.pojo.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "tb_vote_record",indexes = {
        @Index(name = "idx_user_vote",columnList = "voter_id , vote_option_id")
})
@Entity
public class VoteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private User voter;

    @ManyToOne
    @JoinColumn(name = "vote_option_id")
    private VoteOption voteOption;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

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
