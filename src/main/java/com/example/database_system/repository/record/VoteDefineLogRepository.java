package com.example.database_system.repository.record;

import com.example.database_system.pojo.record.VoteDefineLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteDefineLogRepository extends JpaRepository<VoteDefineLog, UUID> {
}
