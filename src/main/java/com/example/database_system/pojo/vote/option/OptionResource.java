package com.example.database_system.pojo.vote.option;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

//存储URL (图片/音乐)

@Entity
@Table(name = "tb_option_resource" ,indexes = {
        @Index(name = "idx_option_resource_vote_option_id", columnList = "vote_option_id")
})
public class OptionResource {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;
    @Pattern(regexp = "^(https?|ftp)://[^ /$.?#].[^ ]*$", message = "URL must be a valid URL")
    private String url;
    @Pattern(regexp = "^(image|audio|video)$", message = "Type must be one of: image, audio, video")
    private String type; // 例如 "image", "audio", "video" 等
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "vote_option_id")
    private VoteOption voteOption;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public OptionResource() {
    }

    public OptionResource(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
