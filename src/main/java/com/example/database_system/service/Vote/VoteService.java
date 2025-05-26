package com.example.database_system.service.Vote;

import com.example.database_system.pojo.dto.VoteDto;
import com.example.database_system.pojo.dto.VoteOptionDto;
import com.example.database_system.pojo.record.VoteDefineLog;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.vote.Vote;
import com.example.database_system.pojo.vote.VoteOption;
import com.example.database_system.repository.UserRepository;
import com.example.database_system.repository.VoteDefineLogRepository;
import com.example.database_system.repository.VoteOptionRepository;
import com.example.database_system.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class VoteService {
    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteOptionRepository voteOptionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteDefineLogRepository voteDefineLogRepository;

    //创建Vote投票 (要携带vote optional信息)

    //todo 这里不算很完整 ticketLimit需要设置
    public ResponseMessage<VoteDto> createVote(List<VoteOptionDto> voteOptionDtoList, VoteDto voteDto, UUID userId) {
        if (voteOptionDtoList.isEmpty()) {
            return ResponseMessage.error(null, "Empty vote options", HttpStatus.BAD_REQUEST.value());
        }
        //创建投票
        var creator = userRepository.findById(userId);
        if (creator.isEmpty()) {
            return ResponseMessage.error(null, "Creator not exist", HttpStatus.BAD_REQUEST.value());
        }
        Vote newVote = new Vote();
        newVote.setTitle(voteDto.getTitle());
        newVote.setDescription(voteDto.getDescription());
        newVote.setStartTime(voteDto.getStartTime());
        newVote.setEndTime(voteDto.getEndTime());
        newVote.setCreatorId(creator.get());

        voteRepository.save(newVote);
        //创建投票选项
        for (VoteOptionDto voteOptionDto : voteOptionDtoList) {
            VoteOption newVoteOption = new VoteOption();
            newVoteOption.setPosition(voteOptionDto.getPosition());
            newVoteOption.setDescription(voteOptionDto.getDescription());
            newVoteOption.setVote(newVote);
            voteOptionRepository.save(newVoteOption);
        }
        //新增一条log
        VoteDefineLog voteDefineLog = new VoteDefineLog();
        voteDefineLog.setVote(newVote);
        voteDefineLog.setOperation("CREATE");
        voteDefineLog.setTime(new Timestamp(System.currentTimeMillis()));
        voteDefineLog.setOperator(creator.get());
        voteDefineLogRepository.save(voteDefineLog);

        return ResponseMessage.success(voteDto, "Create Vote success");
    }

    //获取所有的vote投票
    public ResponseMessage<List<VoteDto>> getAllVote() {
        List<VoteDto> voteDtoList = new ArrayList<>();
        List<Vote> voteList = voteRepository.findAll();
        for (Vote vote : voteList) {
            VoteDto voteDto = new VoteDto();
            voteDto.setTitle(vote.getTitle());
            voteDto.setDescription(vote.getDescription());
            voteDto.setStartTime(vote.getStartTime());
            voteDto.setEndTime(vote.getEndTime());
            voteDto.setTimeout(vote.getEndTime().before(new Timestamp(System.currentTimeMillis())));
            voteDtoList.add(voteDto);
        }
        return ResponseMessage.success(voteDtoList, "success");
    }

    //删除当前的投票
    public ResponseMessage<VoteDto> deleteVote(UUID voteId, UUID userId) {

        //查看表是否存在
        var vote = voteRepository.findById(voteId);
        if (vote.isEmpty()) {
            return ResponseMessage.error(null, "Vote not exist", HttpStatus.BAD_REQUEST.value());
        }

        VoteDto voteDto = new VoteDto();
        voteDto.setTitle(vote.get().getTitle());
        voteDto.setDescription(vote.get().getDescription());
        voteDto.setStartTime(vote.get().getStartTime());
        voteDto.setEndTime(vote.get().getEndTime());
        voteDto.setTimeout(vote.get().getEndTime().before(new Timestamp(System.currentTimeMillis())));
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseMessage.error(voteDto, "User not exist", HttpStatus.BAD_REQUEST.value());
        }

        //判断是否是创建者或者管理者 否则无权删除
        if (user.get().getRole() == 0 && user.get().getId() != vote.get().getCreatorId().getId()) {
            //无法删除
            return ResponseMessage.error(voteDto, "User can not delete this vote", HttpStatus.UNAUTHORIZED.value());
        }

        //删除
        voteRepository.delete(vote.get());

        return ResponseMessage.success(voteDto, "Delete success");


    }

    //下面是详情页的service接口

    //获取当前的vote所有信息(用于生成vote详情页)
    public ResponseMessage<AbstractMap.SimpleEntry<Vote, List<VoteOption>>> getDetailVote(UUID voteId) {
        return voteRepository.findById(voteId).map(value -> ResponseMessage.success(new AbstractMap.SimpleEntry<>(value, value.getVoteOptions()), "success")).orElseGet(()
                -> ResponseMessage.error(null, "Vote not exist", HttpStatus.BAD_REQUEST.value()));
    }

    //为某个选项进行投票
    public ResponseMessage<String> voteFor(UUID voteId, UUID voteOptionId, UUID userId) {

    }
}

