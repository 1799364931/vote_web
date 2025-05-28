package com.example.database_system.repository;

import com.example.database_system.pojo.record.VoteRecord;
import com.example.database_system.pojo.user.User;
import com.example.database_system.pojo.vote.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, UUID> {

    //统计某个用户在某个投票选项中，某个ticket的投票次数
    @Query(value =
            "SELECT COUNT(*) AS cnt FROM tb_vote_record" +
                    "WHERE voter_id = :user_id AND vote_option_id = :vote_option_id" +
                    "GROUP BY ticket_id" +
                    "Having ticket_id = :ticket_id",
            nativeQuery = true)
    public Integer statisticUserVoteRecordCountGroupByTicket(
            @Param("user_id") UUID userId,
            @Param("vote_option_id") UUID voteOptionId,
            @Param("ticket_id") UUID ticketId);
}

