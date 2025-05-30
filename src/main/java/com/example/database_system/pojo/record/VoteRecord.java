package com.example.database_system.pojo.record;

import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.vote.Vote;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_vote_record")
public class VoteRecord {
    @EmbeddedId
    private VoteRecordId voteId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("voteId")
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "vote_count")
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
}


@Embeddable
class VoteRecordId{
    private UUID userId;
    private UUID voteId;
    private UUID ticketId;

    public VoteRecordId() {
    }
    public VoteRecordId(UUID userId, UUID voteId, UUID ticketId) {
        this.userId = userId;
        this.voteId = voteId;
        this.ticketId = ticketId;
    }

    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public UUID getVoteId() {
        return voteId;
    }
    public void setVoteId(UUID voteId) {
        this.voteId = voteId;
    }
    public UUID getTicketId() {
        return ticketId;
    }
    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteRecordId that)) return false;

        if (!userId.equals(that.userId)) return false;
        if (!voteId.equals(that.voteId)) return false;
        return ticketId.equals(that.ticketId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + voteId.hashCode();
        result = 31 * result + ticketId.hashCode();
        return result;
    }
}