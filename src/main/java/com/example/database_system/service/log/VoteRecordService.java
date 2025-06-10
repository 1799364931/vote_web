package com.example.database_system.service.log;

import com.example.database_system.pojo.dto.VoteOptionDto;
import com.example.database_system.pojo.dto.VoteRecordDto;
import com.example.database_system.pojo.dto.ticket.TicketDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.repository.record.VoteOptionRecordRepository;
import com.example.database_system.repository.vote.VoteOptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VoteRecordService {
    private final VoteOptionRecordRepository voteOptionRecordRepository;

    @Autowired
    public VoteRecordService(VoteOptionRecordRepository voteOptionRecordRepository) {
        this.voteOptionRecordRepository = voteOptionRecordRepository;
    }

    public ResponseMessage<List<VoteRecordDto>> getVoteLog(UUID userId) {
        List<VoteRecordDto> voteRecordDtoList = new ArrayList<>();
        var voteRecords = voteOptionRecordRepository.findByUserId(userId);
        for(var voteRecord : voteRecords) {
            VoteRecordDto voteRecordDto = new VoteRecordDto();
            voteRecordDto.setVoteId(voteRecord.getVoteOption().getVote().getId());
            voteRecordDto.setVoteTitle(voteRecord.getVoteOption().getVote().getTitle());
            voteRecordDto.setVoteOption(new VoteOptionDto(voteRecord.getVoteOption()));
            voteRecordDto.setTicket(new TicketDto(voteRecord.getTicket()));
            voteRecordDto.setVoteTime(voteRecord.getTime());
            voteRecordDtoList.add(voteRecordDto);
        }
        return ResponseMessage.success(voteRecordDtoList,"success");
    }


}
