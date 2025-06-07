package com.example.database_system.controller;

import com.example.database_system.pojo.dto.VoteRecordDto;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.util.JwtUtils;
import com.example.database_system.service.log.VoteRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("vote-log")
@Tag(name = "投票记录管理", description = "投票记录相关接口")
public class VoteLogController {

    private final JwtUtils jwtUtils;
    private final VoteRecordService voteRecordService;

    @Autowired
    public VoteLogController(VoteRecordService voteRecordService, JwtUtils jwtUtils) {
        this.voteRecordService = voteRecordService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("api/get-vote-log")
    @Operation(summary = "获取用户投票记录", description = "用于获取当前用户的投票记录，需鉴权")
    public ResponseMessage<List<VoteRecordDto>> getVoteLog(HttpServletRequest request) {
        UUID userId = jwtUtils.getIdFromToken(request.getHeader("Authorization").replace("Bearer", ""));
        return voteRecordService.getVoteLog(userId);
    }

//    @GetMapping("api/get-define-log")
//    @Operation(summary = "获取用户定义的投票记录", description = "用于获取当前用户定义的投票记录，需鉴权")
//    public ResponseEntity<ResponseMessage<List<VoteRecordDto>>> getDefineLog(HttpServletRequest request) {
//        UUID userId = jwtUtils.getIdFromToken(request.getHeader("Authorization").replace("Bearer", ""));
//        return ResponseEntity.ok(voteRecordService.getVoteLog(userId));
//    }
}
