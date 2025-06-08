package com.example.database_system.service;

import com.example.database_system.pojo.dto.ticket.TicketDto;
import com.example.database_system.pojo.dto.ticket.TicketLimitDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.repository.ticket.TicketLimitRepository;
import com.example.database_system.repository.ticket.TicketRepository;
import com.example.database_system.repository.user.UserRepository;
import com.example.database_system.repository.vote.VoteRepository;
import com.example.database_system.repository.record.VoteOptionRecordRepository;
import com.example.database_system.repository.record.VoteRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketLimitRepository ticketLimitRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteRecordRepository voteRecordRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                                 TicketLimitRepository ticketLimitRepository,
                                 VoteRepository voteRepository,
                                 UserRepository userRepository,
                                 VoteRecordRepository voteRecordRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketLimitRepository = ticketLimitRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.voteRecordRepository = voteRecordRepository;
    }

    public ResponseMessage<List<TicketDto>> getAllTickets() {
        List<Ticket> ticketList = ticketRepository.findAll();
        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketDtoList.add(new TicketDto(ticket));
        }
        if (ticketDtoList.isEmpty()) {
            return ResponseMessage.error(new ArrayList<>(), "No tickets found", HttpStatus.BAD_REQUEST.value());
        }
        return ResponseMessage.success(ticketDtoList, "Tickets retrieved successfully");
    }

    //获取剩余票数
    public ResponseMessage<List<TicketLimitDto>> getVoteTickets(UUID userId,UUID voteId){
        var vote = voteRepository.findById(voteId);
        var user = userRepository.findById(userId);
        if(vote.isEmpty() || user.isEmpty()) {
            return ResponseMessage.error(new ArrayList<>(), "vote or user not found", HttpStatus.NOT_FOUND.value());
        }
        //查询当前用户的投票记录
        List<TicketLimitDto> ticketLimitDtoList = new ArrayList<>();
        List<Ticket> ticketList = ticketRepository.findAll();
        for (Ticket ticket : ticketList) {
            var ticketLimit = ticketLimitRepository.findByVoteAndTicket(vote.get(), ticket);
            if (ticketLimit != null && ticketLimit.getCount() > 0) {
                var statisticUserVoteRecordCountGroupByTicketRes = voteRecordRepository.findByUserAndVoteAndTicket(
                        user.get(),
                        vote.get(),
                        ticket);
                Integer userVoteCount = statisticUserVoteRecordCountGroupByTicketRes != null ? statisticUserVoteRecordCountGroupByTicketRes.getVoteCount(): 0;
                TicketLimitDto ticketLimitDto = new TicketLimitDto(ticketLimit);
                ticketLimitDto.setVoteCount(ticketLimit.getCount() - userVoteCount);
                ticketLimitDtoList.add(ticketLimitDto);
            }
        }
        if (ticketLimitDtoList.isEmpty()) {
            return ResponseMessage.error(new ArrayList<>(), "No tickets found", HttpStatus.BAD_REQUEST.value());
        }
        return ResponseMessage.success(ticketLimitDtoList, "Tickets retrieved successfully");
    }
}
