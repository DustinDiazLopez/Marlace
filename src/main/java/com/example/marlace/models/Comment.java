package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentId;

    @JoinColumn(name = "issueId", referencedColumnName = "issueId")
    private Issue issue;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private User createdBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "repliedTo", referencedColumnName = "commentId")
    private Comment repliedTo;

    private String comment;
    private Boolean hasReplies;
    private Integer likes;
    private Integer dislikes;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
