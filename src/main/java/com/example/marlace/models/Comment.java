package com.example.marlace.models;

import java.sql.Timestamp;

public class Comment {
    private Integer commentId;
    private Issue issue;
    private User createdBy;
    private String comment;
    private Comment repliedTo;
    private Boolean hasReplies;
    private Integer likes;
    private Integer dislikes;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
