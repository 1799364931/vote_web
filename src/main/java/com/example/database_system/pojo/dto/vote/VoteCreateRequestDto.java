package com.example.database_system.pojo.dto.vote;

import com.example.database_system.pojo.dto.OptionResourceDto;
import com.example.database_system.pojo.dto.VoteOptionDto;
import com.example.database_system.pojo.dto.ticket.TicketLimitDto;
import com.example.database_system.pojo.dto.vote.VoteDto;

import java.util.List;

public class VoteCreateRequestDto {

    private List<VoteOptionDto> voteOptionDtoList;
    private VoteDto voteDto;
    private List<TicketLimitDto> ticketLimitDtoList;
    private List<OptionResourceDto> optionResourceDtoList;

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

    public List<OptionResourceDto> getOptionResourceDtoList() {
        return optionResourceDtoList;
    }

    public void setOptionResourceDtoList(List<OptionResourceDto> optionResourceDtoList) {
        this.optionResourceDtoList = optionResourceDtoList;
    }
}
