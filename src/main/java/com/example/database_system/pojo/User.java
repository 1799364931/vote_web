package com.example.database_system.pojo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.UUID;

@Table(name = "tb_user",indexes = {
        @Index(name = "account",columnList = "account")
})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",unique = true,nullable = false)
    private UUID id;
    @Column(name = "account",unique = true,nullable = false)
    private String account;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "role")
    private Integer role;

    @OneToMany(mappedBy = "creatorId")
    private ArrayList<Vote> votes;

    @OneToMany(mappedBy = "operator")
    private ArrayList<VoteDefineLog> voteDefineLogs;
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    public ArrayList<VoteDefineLog> getVoteDefineLogs() {
        return voteDefineLogs;
    }

    public void setVoteDefineLogs(ArrayList<VoteDefineLog> voteDefineLogs) {
        this.voteDefineLogs = voteDefineLogs;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}


