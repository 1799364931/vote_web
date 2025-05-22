package com.example.database_system.pojo.user;

import com.example.database_system.pojo.Vote;
import com.example.database_system.pojo.VoteDefineLog;
import com.example.database_system.pojo.dto.LoginRegisterUserDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
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
    private List<Vote> votes= new ArrayList<>(); ;

    @OneToMany(mappedBy = "operator")
    private List<VoteDefineLog> voteDefineLogs = new ArrayList<>(); ;

    public User(){
        this.name = "default_name";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    public List<VoteDefineLog> getVoteDefineLogs() {
        return voteDefineLogs;
    }

    public void setVoteDefineLogs(List<VoteDefineLog> voteDefineLogs) {
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


