package com.example.database_system.repository;

import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.pojo.ticket.TicketLimit;
import com.example.database_system.pojo.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketLimitRepository extends JpaRepository<TicketLimit, UUID> {

    //统计某个投票的某个票的限制票数
    public Integer findDistinctByVoteAndTicket(Vote voteId, Ticket ticketId);
}
