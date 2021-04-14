package com.example.marlace.models;

import com.example.marlace.utilities.Constants;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
public @Data
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    private Issue issue;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "userId")
    private User createdBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "commentId")
    private Comment repliedTo = null;

    @Column(columnDefinition = Constants.ColumnDefinitions.TEXT)
    private String comment;

    @Column
    private Boolean hasReplies = Boolean.FALSE;

    @Column
    private Integer likes = 0;

    @Column
    private Integer dislikes = 0;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
