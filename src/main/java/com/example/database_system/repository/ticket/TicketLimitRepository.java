package com.example.database_system.repository.ticket;

import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.pojo.ticket.TicketLimit;
import com.example.database_system.pojo.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TicketLimitRepository extends JpaRepository<TicketLimit, UUID> {

    //统计某个投票的某个票的限制票数
    public Integer findDistinctByVoteAndTicket(Vote voteId, Ticket ticketId);

    //
    public TicketLimit findByVoteAndTicket(Vote voteId, Ticket ticketId);
}
