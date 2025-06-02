package com.example.database_system.repository.record;

import com.example.database_system.pojo.record.VoteOptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface VoteOptionRecordRepository extends JpaRepository<VoteOptionRecord, UUID> {

    //统计某个用户在某个投票中，某个ticket的投票次数
    @Query(value =
            "SELECT COUNT(*) AS cnt FROM tb_vote_record " +
                    "WHERE voter_id = :user_id AND vote_option_id = :vote_id AND ticket_id = :ticket_id " +
                    "GROUP BY ticket_id " +
                    "HAVING ticket_id = :ticket_id",
            nativeQuery = true)
    public List<Integer> statisticUserVoteRecordCountGroupByTicket(
            @Param("user_id") UUID userId,
            @Param("vote_id") UUID voteId,
            @Param("ticket_id") UUID ticketId);

    //统计某个用户在某个投票 中所有ticket的投票情况
    @Query(value =
            "SELECT COUNT(*) AS cnt FROM tb_vote_record" +
                    "WHERE voter_id = :user_id AND vote_option_id = :vote_option_id" +
                    "GROUP BY ticket_id" +
                    "Having ticket_id = :ticket_id",
            nativeQuery = true)
    public List<Integer> statisticUserVoteRecordCountGroupByTicket(
            @Param("user_id") UUID userId,
            @Param("vote_option_id") UUID voteOptionId);
}

