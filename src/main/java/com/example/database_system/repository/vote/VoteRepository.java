package com.example.database_system.repository.vote;

import com.example.database_system.pojo.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    //模糊搜索
    List<Vote> findByTitleContainingOrDescriptionContaining(String title, String description);
}
