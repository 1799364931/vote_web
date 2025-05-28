package com.example.database_system.pojo.dto;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class VoteCreateRequestDto {

    private List<VoteOptionDto> voteOptionDtoList;
    private VoteDto voteDto;
    private List<TicketLimitDto> ticketLimitDtoList;

    public List<VoteOptionDto> getVoteOptionDtoList() {
        return voteOptionDtoList;
    }

    public void setVoteOptionDtoList(List<VoteOptionDto> voteOptionDtoList) {
        this.voteOptionDtoList = voteOptionDtoList;
    }

    public VoteDto getVoteDto() {
        return voteDto;
    }

    public void setVoteDto(VoteDto voteDto) {
        this.voteDto = voteDto;
    }

    public List<TicketLimitDto> getTicketLimitDtoList() {
        return ticketLimitDtoList;
    }

    public void setTicketLimitDtoList(List<TicketLimitDto> ticketLimitDtoList) {
        this.ticketLimitDtoList = ticketLimitDtoList;
    }
}
