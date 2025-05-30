package com.example.database_system.repository.vote;

import com.example.database_system.pojo.vote.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteOptionRepository extends JpaRepository<VoteOption, UUID> {
}
