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

    // 查询用户的投票记录
    @Query(value = "SELECT * FROM tb_vote_option_record  WHERE voter_id = :userId",nativeQuery = true)
    List<VoteOptionRecord> findByUserId(@Param("userId") UUID userId);
}

