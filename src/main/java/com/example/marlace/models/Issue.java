package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer issueId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project project;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tagId", referencedColumnName = "tagId")
    private Tag tag;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private User createdBy;

//    @ManyToOne
//    @JoinColumn(name = "comments")
//    private Set<Comment> comments;

    private String title;
    private String body;
    private Boolean resolved;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
