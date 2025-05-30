package com.example.database_system.pojo.dto.vote;

import com.example.database_system.pojo.dto.VoteOptionDto;
import com.example.database_system.pojo.dto.ticket.TicketLimitDto;
import com.example.database_system.pojo.dto.user.UserDto;

import java.util.List;

public class VoteDetailDto {
    private VoteDto voteDto;
    private UserDto creator;
    private List<VoteOptionDto> voteOptionDtoList;
    private List<TicketLimitDto> ticketLimitDtoList;

    public VoteDto getVoteDto() {
        return voteDto;
    }

    public void setVoteDto(VoteDto voteDto) {
        this.voteDto = voteDto;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }

    public List<VoteOptionDto> getVoteOptionDtoList() {
        return voteOptionDtoList;
    }

    public void setVoteOptionDtoList(List<VoteOptionDto> voteOptionDtoList) {
        this.voteOptionDtoList = voteOptionDtoList;
    }

    public List<TicketLimitDto> getTicketLimitDtoList() {
        return ticketLimitDtoList;
    }

    public void setTicketLimitDtoList(List<TicketLimitDto> ticketLimitDtoList) {
        this.ticketLimitDtoList = ticketLimitDtoList;
    }
}
