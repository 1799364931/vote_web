package com.example.database_system.service.Vote;

import com.example.database_system.pojo.dto.*;
import com.example.database_system.pojo.dto.ticket.TicketLimitDto;
import com.example.database_system.pojo.dto.user.UserDto;
import com.example.database_system.pojo.dto.vote.VoteDetailDto;
import com.example.database_system.pojo.dto.vote.VoteDto;
import com.example.database_system.pojo.record.VoteDefineLog;
import com.example.database_system.pojo.record.VoteOptionRecord;
import com.example.database_system.pojo.record.VoteRecord;
import com.example.database_system.pojo.record.VoteRecordId;
import com.example.database_system.pojo.response.ResponseMessage;
import com.example.database_system.pojo.ticket.TicketLimit;
import com.example.database_system.pojo.vote.Vote;
import com.example.database_system.pojo.vote.option.OptionResource;
import com.example.database_system.pojo.vote.option.VoteOption;
import com.example.database_system.repository.ticket.TicketLimitRepository;
import com.example.database_system.repository.ticket.TicketRepository;
import com.example.database_system.repository.user.UserRepository;
import com.example.database_system.repository.vote.OptionResourceRepository;
import com.example.database_system.repository.vote.VoteOptionRepository;
import com.example.database_system.repository.vote.VoteRepository;
import com.example.database_system.repository.record.VoteDefineLogRepository;
import com.example.database_system.repository.record.VoteOptionRecordRepository;
import com.example.database_system.repository.record.VoteRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final UserRepository userRepository;
    private final VoteDefineLogRepository voteDefineLogRepository;
    private final TicketRepository ticketRepository;
    private final TicketLimitRepository ticketLimitRepository;
    private final VoteOptionRecordRepository voteOptionRecordRepository;
    private final VoteRecordRepository voteRecordRepository;
    private final OptionResourceRepository optionResourceRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository,
                       VoteOptionRepository voteOptionRepository,
                       UserRepository userRepository,
                       VoteDefineLogRepository voteDefineLogRepository,
                       TicketRepository ticketRepository,
                       TicketLimitRepository ticketLimitRepository,
                       VoteOptionRecordRepository voteOptionRecordRepository,
                       VoteRecordRepository voteRecordRepository,
                       OptionResourceRepository optionResourceRepository) {
        this.voteRepository = voteRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.userRepository = userRepository;
        this.voteDefineLogRepository = voteDefineLogRepository;
        this.ticketRepository = ticketRepository;
        this.ticketLimitRepository = ticketLimitRepository;
        this.voteOptionRecordRepository = voteOptionRecordRepository;
        this.voteRecordRepository = voteRecordRepository;
        this.optionResourceRepository = optionResourceRepository;
    }

    //创建投票
    public ResponseMessage<UUID> createVote(List<VoteOptionDto> voteOptionDtoList, VoteDto voteDto, UUID userId, List<TicketLimitDto> ticketLimitDtoList) {
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

            if(voteOptionDto.getResourceUrl() != null && !voteOptionDto.getResourceUrl().isEmpty()) {
                //如果有资源链接就设置
                OptionResource optionResource = new OptionResource();
                optionResource.setUrl(voteOptionDto.getResourceUrl());
                optionResource.setVoteOption(newVoteOption);
                optionResource.setType("image"); // 默认类型为图片
                optionResourceRepository.save(optionResource);
            }
        }

        //新建一个票数限制
        for (TicketLimitDto ticketLimitDto : ticketLimitDtoList) {
            //存储
            TicketLimit ticketLimit = new TicketLimit();
            var ticket = ticketRepository.findById(ticketLimitDto.getTicketId());
            if (ticket.isEmpty()) {
                return ResponseMessage.error(null, "ticket not exist", HttpStatus.BAD_REQUEST.value());
            }
            ticketLimit.setTicket(ticket.get());
            ticketLimit.setCount(ticketLimitDto.getVoteCount());
            ticketLimit.setVote(newVote);

            ticketLimitRepository.save(ticketLimit);
        }

        //新增一条log
        VoteDefineLog voteDefineLog = new VoteDefineLog("CREATE", new Timestamp(System.currentTimeMillis()), creator.get(), newVote);
        voteDefineLogRepository.save(voteDefineLog);
        return ResponseMessage.success(newVote.getId(), "Create vote success");
    }

    //获取所有的vote投票
    public ResponseMessage<List<VoteDto>> getAllVote() {
        List<VoteDto> voteDtoList = new ArrayList<>();
        List<Vote> voteList = voteRepository.findAll();
        for( Vote vote : voteList) {
            if(vote.getDelete()) {
                continue;
            }
            voteDtoList.add(new VoteDto(vote));
        }
        return ResponseMessage.success(voteDtoList, "success");
    }

    //删除当前的投票
    public ResponseMessage<VoteDto> deleteVote(UUID voteId, UUID userId) {

        //查看表是否存在
        var vote = voteRepository.findById(voteId);
        if (vote.isEmpty()) {
            return ResponseMessage.error(null, "vote not exist", HttpStatus.BAD_REQUEST.value());
        }

        VoteDto voteDto = new VoteDto(vote.get());
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseMessage.error(voteDto, "user not exist", HttpStatus.BAD_REQUEST.value());
        }

        //判断是否是创建者或者管理者 否则无权删除
        if (user.get().getRole() == 0 && user.get().getId() != vote.get().getCreatorId().getId()) {
            //无法删除
            return ResponseMessage.error(voteDto, "user can not delete this vote", HttpStatus.UNAUTHORIZED.value());
        }

        vote.get().setDelete(true);
        //删除
        voteRepository.save(vote.get());
        //新建一条log
        VoteDefineLog voteDefineLog = new VoteDefineLog("DELETE", new Timestamp(System.currentTimeMillis()), user.get(), vote.get());
        voteDefineLogRepository.save(voteDefineLog);

        return ResponseMessage.success(voteDto, "Delete success");

    }

    //下面是详情页的service接口

    //获取当前的vote所有信息(用于生成vote详情页)
    public ResponseMessage<VoteDetailDto> getDetailVote(UUID voteId,UUID userId) {
        VoteDetailDto voteDetailDto = new VoteDetailDto();
        //获取投票的创建者
        var vote = voteRepository.findById(voteId);
        List<VoteOptionDto> voteOptionDtoList = new ArrayList<>();
        List<TicketLimitDto> ticketLimitDtoList = new ArrayList<>();

        if (vote.isEmpty()) {
            return ResponseMessage.error(null, "vote not exist", HttpStatus.BAD_REQUEST.value());
        }

        for (int i = 0; i < vote.get().getVoteOptions().size(); i++) {
            voteOptionDtoList.add(new VoteOptionDto(vote.get().getVoteOptions().get(i)));
            //设置资源链接
            var optionResource = optionResourceRepository.findByVoteOption(vote.get().getVoteOptions().get(i));
            if (optionResource.isPresent()) {
                voteOptionDtoList.get(i).setResourceUrl(optionResource.get().getUrl());
            } else {
                voteOptionDtoList.get(i).setResourceUrl(null);
            }
        }

        // limitDto 的票数是剩余可投票数
        //如果用户未登录
        if(userId == null){
            for (var ticketLimit : vote.get().getTicketLimits()) {
                TicketLimitDto ticketLimitDto = new TicketLimitDto(ticketLimit);
                ticketLimitDtoList.add(ticketLimitDto);
            }
            voteDetailDto.setOwner(false);
        }
        else{
            //如果用户登录了
            var user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return ResponseMessage.error(null, "user not exist", HttpStatus.BAD_REQUEST.value());
            }
            //判断是否是创建者
            voteDetailDto.setOwner(user.get().getId() == vote.get().getCreatorId().getId() || user.get().getRole() == 0);
            for (var ticketLimit : vote.get().getTicketLimits()) {
                TicketLimitDto ticketLimitDto = new TicketLimitDto(ticketLimit);
                //获取当前用户在当前投票中投的所有票
                var statisticUserVoteRecordCountGroupByTicketRes = voteRecordRepository.findByUserAndVoteAndTicket(
                        user.get(),
                        vote.get(),
                        ticketLimit.getTicket());
                if(statisticUserVoteRecordCountGroupByTicketRes == null){
                    statisticUserVoteRecordCountGroupByTicketRes = new VoteRecord();
                    statisticUserVoteRecordCountGroupByTicketRes.setUser(user.get());
                    statisticUserVoteRecordCountGroupByTicketRes.setVote(vote.get());
                    statisticUserVoteRecordCountGroupByTicketRes.setTicket(ticketLimit.getTicket());
                    statisticUserVoteRecordCountGroupByTicketRes.setVoteCount(0);
                    statisticUserVoteRecordCountGroupByTicketRes.setVoteId(
                            new VoteRecordId(user.get().getId(), vote.get().getId(), ticketLimit.getTicket().getId())
                    );
                }
                Integer limitCount = ticketLimitRepository.findByVoteAndTicket(vote.get(), ticketLimit.getTicket()).getCount();
                ticketLimitDto.setVoteCount(limitCount - statisticUserVoteRecordCountGroupByTicketRes.getVoteCount());
                ticketLimitDtoList.add(ticketLimitDto);
            }
        }
        voteDetailDto.setVoteDto(new VoteDto(vote.get()));
        voteDetailDto.setCreator(new UserDto(vote.get().getCreatorId()));
        voteDetailDto.setVoteOptionDtoList(voteOptionDtoList);
        voteDetailDto.setTicketLimitDtoList(ticketLimitDtoList);
        return ResponseMessage.success(voteDetailDto, "success");
    }

    //为某个投票选项投票
    public ResponseMessage<Integer> voteFor(UUID voteId, UUID voteOptionId, UUID userId, UUID ticketId) {
        //查看投票的东西是否存在
        var user = userRepository.findById(userId);
        var vote = voteRepository.findById(voteId);
        var voteOption = voteOptionRepository.findById(voteOptionId);
        var ticket = ticketRepository.findById(ticketId);
        if (user.isEmpty() || vote.isEmpty() || voteOption.isEmpty() || ticket.isEmpty()) {
            return ResponseMessage.error(null, "Something not exist", HttpStatus.BAD_REQUEST.value());
        }
        //查看投票是否结束 or 开始
        if(vote.get().getStartTime().after(new Timestamp(System.currentTimeMillis()))
        ||vote.get().getEndTime().before(new Timestamp(System.currentTimeMillis()))){
            return ResponseMessage.error(null,"not in vote time",HttpStatus.BAD_REQUEST.value());
        }


        //查看当前用户是否允许投票
        //统计当前用户在当前投票中投的所有票 (如果该用户的投票达到了投票上限就不再允许投票
        var statisticUserVoteRecordCountGroupByTicketRes = voteRecordRepository.findByUserAndVoteAndTicket(
                user.get(),
                vote.get(),
                ticket.get());
        if(statisticUserVoteRecordCountGroupByTicketRes == null){
            statisticUserVoteRecordCountGroupByTicketRes = new VoteRecord();
            statisticUserVoteRecordCountGroupByTicketRes.setUser(user.get());
            statisticUserVoteRecordCountGroupByTicketRes.setVote(vote.get());
            statisticUserVoteRecordCountGroupByTicketRes.setTicket(ticket.get());
            statisticUserVoteRecordCountGroupByTicketRes.setVoteCount(0);
            statisticUserVoteRecordCountGroupByTicketRes.setVoteId(
                    new VoteRecordId(user.get().getId(), vote.get().getId(), ticket.get().getId())
            );
        }

        Integer limitCount = ticketLimitRepository.findByVoteAndTicket(vote.get(), ticket.get()).getCount();
        if (statisticUserVoteRecordCountGroupByTicketRes.getVoteCount() >= limitCount) {
            return ResponseMessage.error(null, "Reach this ticket limit count", HttpStatus.BAD_REQUEST.value());
        }
        //投票
        VoteOptionRecord voteOptionRecord = new VoteOptionRecord();
        voteOptionRecord.setVoteOption(voteOption.get());
        voteOptionRecord.setTicket(ticket.get());
        voteOptionRecord.setVoter(user.get());
        voteOptionRecord.setTime(new Timestamp(System.currentTimeMillis()));

        voteOption.get().addVoteCount(ticket.get().getWeight());
        //记录投票
        statisticUserVoteRecordCountGroupByTicketRes.addVoteCount(1);
        voteOptionRecordRepository.save(voteOptionRecord);
        voteRecordRepository.save(statisticUserVoteRecordCountGroupByTicketRes);
        return ResponseMessage.success(limitCount - statisticUserVoteRecordCountGroupByTicketRes.getVoteCount(), "success");
    }

    //撤销某个投票
    public ResponseMessage<String> revokeVote(UUID voteRecordId, UUID userId) {
        //查看投票记录是否存在
        var voteRecord = voteOptionRecordRepository.findById(voteRecordId);
        if (voteRecord.isEmpty()) {
            return ResponseMessage.error(null, "vote record not exist", HttpStatus.BAD_REQUEST.value());
        }
        //查看用户是否存在
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseMessage.error(null, "user not exist", HttpStatus.BAD_REQUEST.value());
        }
        //判断用户是否是投票的创建者
        if (user.get().getId() != voteRecord.get().getVoter().getId()) {
            return ResponseMessage.error(null, "user can not revoke this vote", HttpStatus.UNAUTHORIZED.value());
        }
        //删除投票记录
        voteOptionRecordRepository.delete(voteRecord.get());
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
            if(vote.getDelete()) {
                continue; //如果是删除的投票就不显示
            }
            voteDtoList.add(new VoteDto(vote));
        }
        return ResponseMessage.success(voteDtoList, "Search success");
    }

    //根据用户ID获取用户创建的投票信息
    public ResponseMessage<List<VoteDto>> getVoteByUser(UUID userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseMessage.error(null, "user not exist", HttpStatus.BAD_REQUEST.value());
        }
        List<Vote> voteList = voteRepository.findByCreatorId(user.get());
        List<VoteDto> voteDtoList = new ArrayList<>();
        for (Vote vote : voteList) {
            if(vote.getDelete()) {
                continue; //如果是删除的投票就不显示
            }
            voteDtoList.add(new VoteDto(vote));
        }
        return ResponseMessage.success(voteDtoList, "Get user's votes success");
    }
}

