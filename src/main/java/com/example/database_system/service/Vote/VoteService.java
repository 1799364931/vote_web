package com.example.database_system.service.Vote;

import com.example.database_system.pojo.dto.*;
import com.example.database_system.pojo.record.VoteDefineLog;
import com.example.database_system.pojo.record.VoteRecord;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.ticket.TicketLimit;
import com.example.database_system.pojo.vote.Vote;
import com.example.database_system.pojo.vote.VoteOption;
import com.example.database_system.repository.*;
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

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketLimitRepository ticketLimitRepository;

    @Autowired
    VoteRecordRepository voteRecordRepository;

    //创建Vote投票 (要携带vote optional信息)

    //todo 这里不算很完整 ticketLimit需要设置
    public ResponseMessage<VoteDto> createVote(List<VoteOptionDto> voteOptionDtoList, VoteDto voteDto, UUID userId, List<TicketLimitDto> ticketLimitDtoList) {
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
        //新建一个票数限制
        for (TicketLimitDto ticketLimitDto : ticketLimitDtoList) {
            //存储
            TicketLimit ticketLimit = new TicketLimit();
            var ticket = ticketRepository.findById(ticketLimitDto.getTicketId());
            if (ticket.isEmpty()) {
                return ResponseMessage.error(null, "Ticket not exist", HttpStatus.BAD_REQUEST.value());
            }
            ticketLimit.setTicket(ticket.get());
            ticketLimit.setCount(ticketLimitDto.getCount());
            ticketLimit.setVote(newVote);
            ticketLimitRepository.save(ticketLimit);
        }

        //新增一条log
        VoteDefineLog voteDefineLog = new VoteDefineLog("CREATE", new Timestamp(System.currentTimeMillis()), creator.get(), newVote);
        return ResponseMessage.success(voteDto, "Create Vote success");
    }

    //获取所有的vote投票
    public ResponseMessage<List<VoteDto>> getAllVote() {
        List<VoteDto> voteDtoList = new ArrayList<>();
        List<Vote> voteList = voteRepository.findAll();
        for (Vote vote : voteList) {
            voteDtoList.add(new VoteDto(vote));
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

        VoteDto voteDto = new VoteDto(vote.get());
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
        //新建一条log
        VoteDefineLog voteDefineLog = new VoteDefineLog("DELETE", new Timestamp(System.currentTimeMillis()), user.get(), vote.get());
        voteDefineLogRepository.save(voteDefineLog);

        return ResponseMessage.success(voteDto, "Delete success");

    }

    //下面是详情页的service接口

    //获取当前的vote所有信息(用于生成vote详情页)
    public ResponseMessage<VoteDetailDto> getDetailVote(UUID voteId) {
        VoteDetailDto voteDetailDto = new VoteDetailDto();
        //获取投票的创建者
        var vote = voteRepository.findById(voteId);
        List<VoteOptionDto> voteOptionDtoList = new ArrayList<>();
        List<TicketLimitDto> ticketLimitDtoList = new ArrayList<>();
        if (vote.isEmpty()) {
            return ResponseMessage.error(null, "Vote not exist", HttpStatus.BAD_REQUEST.value());
        }

        for (int i = 0; i < vote.get().getVoteOptions().size(); i++) {
            voteOptionDtoList.add(new VoteOptionDto(vote.get().getVoteOptions().get(i)));
        }

        for (int i = 0; i < vote.get().getTicketLimits().size(); i++) {
            ticketLimitDtoList.add(new TicketLimitDto(vote.get().getTicketLimits().get(i)));
        }

        voteDetailDto.setVoteDto(new VoteDto(vote.get()));
        voteDetailDto.setCreator(new UserDto(vote.get().getCreatorId()));
        voteDetailDto.setVoteOptionDtoList(voteOptionDtoList);
        voteDetailDto.setTicketLimitDtoList(ticketLimitDtoList);
        return ResponseMessage.success(voteDetailDto, "success");
    }

    //为某个选项进行投票
    public ResponseMessage<String> voteFor(UUID voteId, UUID voteOptionId, UUID userId, UUID ticketId) {
        //查看投票的东西是否存在
        var user = userRepository.findById(userId);
        var vote = voteRepository.findById(voteId);
        var voteOption = voteOptionRepository.findById(voteOptionId);
        var ticket = ticketRepository.findById(ticketId);
        if (user.isEmpty() || vote.isEmpty() || voteOption.isEmpty() || ticket.isEmpty()) {
            return ResponseMessage.error(null, "Something not exist", HttpStatus.BAD_REQUEST.value());
        }
        //查看当前用户是否允许投票
        //统计当前用户在当前投票中投的所有票 (如果该用户的投票达到了投票上限就不再允许投票
        Integer statisticUserVoteRecordCountGroupByTicketRes = voteRecordRepository.statisticUserVoteRecordCountGroupByTicket(userId, voteOptionId, voteId);
        Integer limitCount = ticketLimitRepository.findDistinctByVoteAndTicket(vote.get(), ticket.get());
        if (statisticUserVoteRecordCountGroupByTicketRes >= limitCount) {
            return ResponseMessage.error(null, "Reach this ticket limit count", HttpStatus.BAD_REQUEST.value());
        }
        //投票
        VoteRecord voteRecord = new VoteRecord();
        voteRecord.setVoteOption(voteOption.get());
        voteRecord.setTicket(ticket.get());
        voteRecord.setVoter(user.get());
        //记录投票
        voteRecordRepository.save(voteRecord);
        return ResponseMessage.success("success", "success");
    }

    //撤销某个投票
    public ResponseMessage<String> revokeVote(UUID voteRecordId, UUID userId) {
        //查看投票记录是否存在
        var voteRecord = voteRecordRepository.findById(voteRecordId);
        if (voteRecord.isEmpty()) {
            return ResponseMessage.error(null, "Vote record not exist", HttpStatus.BAD_REQUEST.value());
        }
        //查看用户是否存在
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseMessage.error(null, "User not exist", HttpStatus.BAD_REQUEST.value());
        }
        //判断用户是否是投票的创建者
        if (user.get().getId() != voteRecord.get().getVoter().getId()) {
            return ResponseMessage.error(null, "User can not revoke this vote", HttpStatus.UNAUTHORIZED.value());
        }
        //删除投票记录
        voteRecordRepository.delete(voteRecord.get());
        return ResponseMessage.success("success", "Revoke success");
    }

    //搜索投票
    public ResponseMessage<List<VoteDto>> searchVote(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return new ResponseMessage<>(400, "Keyword cannot be empty", new ArrayList<>());
        }
        List<Vote> voteList = voteRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
        List<VoteDto> voteDtoList = new ArrayList<>();
        for (Vote vote : voteList) {
            voteDtoList.add(new VoteDto(vote));
        }
        return ResponseMessage.success(voteDtoList, "Search success");
    }
}

