package com.example.database_system.repository.record;

import com.example.database_system.pojo.record.VoteRecord;
import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, UUID> {

    public VoteRecord findByUserAndVoteAndTicket(User user, Vote vote, Ticket ticket);
}
