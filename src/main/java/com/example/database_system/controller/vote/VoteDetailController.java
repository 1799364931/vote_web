package com.example.database_system.controller.vote;

import com.example.database_system.pojo.util.JwtUtils;
import com.example.database_system.pojo.dto.vote.VoteDetailDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.service.Vote.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/vote-detail")
@Tag(name = "投票详情管理", description = "投票详情相关接口，主要用于获取投票详情和提交投票")
public class VoteDetailController {

    private final VoteService voteService;

    private final JwtUtils jwtUtils;

    @Autowired
    public VoteDetailController(VoteService voteService, JwtUtils jwtUtils) {
        this.voteService = voteService;
        this.jwtUtils = jwtUtils;
    }

    //无需鉴权 这里记得放行
    @GetMapping("/vote={voteId}")
    @Tag(name = "获取投票详情", description = "用于获取指定投票的详细信息，无需鉴权")
    public ResponseMessage<VoteDetailDto> getVote(@PathVariable UUID voteId) {
        return voteService.getDetailVote(voteId);
    }

    //提交投票 这里要鉴权
    @PostMapping("api/vote/vote={voteId}")
    @Tag(name = "提交投票", description = "用于提交对指定投票的投票选项，需鉴权")
    public ResponseMessage<String> voteFor(@PathVariable UUID voteId, @RequestBody UUID ticketId, @RequestBody UUID voteOptionId, HttpServletRequest request) {
        //这里需要先鉴权
        String token = request.getHeader("Authorization").replace("Bearer", "");
        UUID userId = jwtUtils.getIdFromToken(token);
        return voteService.voteFor(voteId, voteOptionId, userId, ticketId);
    }

    //撤销投票 (删除)
}
