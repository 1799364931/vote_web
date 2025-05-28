package com.example.database_system.controller;

import com.example.database_system.pojo.dto.TicketDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    // 获取投票 的票信息
    @Autowired
    TicketService ticketService;

    @GetMapping("api/get-all-tickets")
    public ResponseMessage<List<TicketDto>> getAllTickets() {
        return ticketService.getAllTickets();
    }
}
