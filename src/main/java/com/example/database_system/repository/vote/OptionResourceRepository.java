package com.example.database_system.repository.vote;

import com.example.database_system.pojo.vote.option.OptionResource;
import com.example.database_system.pojo.vote.option.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OptionResourceRepository extends JpaRepository<OptionResource, UUID> {

    public Optional<OptionResource> findByVoteOption(VoteOption voteOption);
}
