package com.example.database_system.repository;

import com.example.database_system.pojo.record.VoteDefineLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteDefineLogRepository extends JpaRepository<VoteDefineLog, UUID> {
}
