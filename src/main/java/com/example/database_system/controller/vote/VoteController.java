package com.example.database_system.controller.vote;

import com.example.database_system.pojo.util.JwtUtils;
import com.example.database_system.pojo.dto.vote.VoteCreateRequestDto;
import com.example.database_system.pojo.dto.vote.VoteDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.service.Vote.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vote")
@Tag(name = "投票管理", description = "投票相关接口，主要用于投票的创建、删除、查询等功能")
public class VoteController {

    private final JwtUtils jwtUtils;

    private final  VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService, JwtUtils jwtUtils) {
        this.voteService = voteService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("api/all-vote")
    @Tag(name = "获取所有投票信息", description = "用于获取系统中所有的投票信息，无需鉴权")
    public ResponseMessage<List<VoteDto>> getAllVote() {
        return voteService.getAllVote();
    }

    @PostMapping("api/create")
    @Tag(name = "创建投票", description = "用于创建新的投票信息，需鉴权")
    public ResponseMessage<UUID> createVote(HttpServletRequest request, @RequestBody VoteCreateRequestDto voteCreateRequestDto) {
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.validateToken(token)? jwtUtils.getIdFromToken(token) : null;
        return voteService.createVote(voteCreateRequestDto.getVoteOptionDtoList(), voteCreateRequestDto.getVoteDto(), userId, voteCreateRequestDto.getTicketLimitDtoList());
    }


    @DeleteMapping("api/delete/{voteId}")
    @Tag(name = "删除投票", description = "用于删除指定的投票信息，需鉴权")
    public ResponseMessage<VoteDto> deleteVote(HttpServletRequest request, @PathVariable UUID voteId) {
        //这里需要先鉴权
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.validateToken(token)? jwtUtils.getIdFromToken(token) : null;
        return voteService.deleteVote(voteId, userId);
    }

    @PostMapping("api/search/keyword={keyword}")
    @Tag(name = "搜索投票", description = "用于根据关键词搜索投票信息，无需鉴权")
    public ResponseMessage<List<VoteDto>> searchVote(@PathVariable String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return new ResponseMessage<>(400, "Keyword cannot be empty", new ArrayList<>());
        }
        return voteService.searchVote(keyword);
    }
}
