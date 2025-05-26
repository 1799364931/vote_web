package com.example.database_system.controller;

import com.example.database_system.pojo.JwtUtils;
import com.example.database_system.pojo.dto.VoteDto;
import com.example.database_system.pojo.dto.VoteOptionDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.vote.VoteOption;
import com.example.database_system.service.Vote.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/home")
public class VoteController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    VoteService voteService;

    @GetMapping("api/allvote")
    public ResponseMessage<List<VoteDto>> getAllVote() {
        return voteService.getAllVote();
    }

    @PostMapping("api/create")
    public ResponseMessage<VoteDto> createVote(HttpServletRequest request, @RequestBody List<VoteOptionDto> voteOptionDtoList, @RequestBody VoteDto voteDto) {
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.getIdFromToken(token);
        return voteService.createVote(voteOptionDtoList, voteDto, userId);
    }

    @DeleteMapping("api/delete/{voteId}")
    public ResponseMessage<VoteDto> deleteVote(HttpServletRequest request, @PathVariable UUID voteId) {
        //这里需要先鉴权
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.getIdFromToken(token);
        return voteService.deleteVote(voteId, userId);
    }
}
