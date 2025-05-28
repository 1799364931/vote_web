package com.example.database_system.controller;

import com.example.database_system.pojo.JwtUtils;
import com.example.database_system.pojo.dto.VoteDetailDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.vote.Vote;
import com.example.database_system.pojo.vote.VoteOption;
import com.example.database_system.service.Vote.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/vote")
public class VoteDetailController {

    @Autowired
    VoteService voteService;

    @Autowired
    JwtUtils jwtUtils;

    //无需鉴权 这里记得放行
    @GetMapping("/{voteId}")
    public ResponseMessage<VoteDetailDto> getVote(@PathVariable UUID voteId) {
        return voteService.getDetailVote(voteId);
    }

    //提交投票 这里要鉴权
    @PostMapping("/{voteId}")
    public ResponseMessage<String> voteFor(@PathVariable UUID voteId, @RequestBody UUID ticketId, @RequestBody UUID voteOptionId, HttpServletRequest request) {
        //这里需要先鉴权
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.getIdFromToken(token);
        return voteService.voteFor(voteId, voteOptionId, userId, ticketId);
    }

    //撤销投票 (删除)
}
