package com.example.database_system.pojo.record;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class VoteRecordId implements Serializable {
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
