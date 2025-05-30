package com.example.database_system.controller.ticket;

import com.example.database_system.pojo.util.JwtUtils;
import com.example.database_system.pojo.dto.ticket.TicketDto;
import com.example.database_system.pojo.dto.ticket.TicketLimitDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
@Tag(name = "票种类信息管理", description = "票种类相关接口")
public class TicketController {

    private final TicketService ticketService;

    private final JwtUtils jwtUtils;

    @Autowired
    public TicketController(TicketService ticketService, JwtUtils jwtUtils) {
        this.ticketService = ticketService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("api/get-all-tickets")
    @Operation(summary = "获取所有可用票类", description = "用于创建投票时获取所有可用的票类信息")
    public ResponseMessage<List<TicketDto>> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("api/get-vote-tickets/{voteId}")
    @Operation(summary = "获取当前某一投票可用票数", description = "用于获取当前投票的可用票数信息，需鉴权")
    public ResponseMessage<List<TicketLimitDto>> getVoteTickets(HttpServletRequest request, @PathVariable UUID voteId) {
        //鉴权
        UUID userId = jwtUtils.getIdFromToken(request.getHeader("Authorization").replace("Bearer", ""));
        return ticketService.getVoteTickets(userId, voteId);
    }
}
