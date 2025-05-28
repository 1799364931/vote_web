package com.example.database_system.service;

import com.example.database_system.pojo.dto.TicketDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.ticket.Ticket;
import com.example.database_system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;


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
}
