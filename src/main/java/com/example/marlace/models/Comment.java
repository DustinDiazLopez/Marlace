package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public @Data
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentId;

    @ManyToOne
    private Issue issue;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "userId")
    private User createdBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "commentId")
    private Comment repliedTo = null;

    @Column
    private String comment;

    @Column
    private Boolean hasReplies = Boolean.FALSE;

    @Column
    private Integer likes = 0;

    @Column
    private Integer dislikes = 0;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;
}
