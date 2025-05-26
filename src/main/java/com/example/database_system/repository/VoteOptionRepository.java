package com.example.database_system.repository;

import com.example.database_system.pojo.vote.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteOptionRepository extends JpaRepository<VoteOption, UUID> {
}
