package com.example.database_system.pojo;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Table (name = "tb_vote_define_manager")
@Entity
public class VoteDefineManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @Column(name = "operation")
    private String operation;
    @Column(name = "time")
    private Timestamp time;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User operator;
    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;
}
