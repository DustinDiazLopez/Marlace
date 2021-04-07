package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "issues")
public @Data
class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer issueId;

    @ManyToOne
    private Project project;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tagId", referencedColumnName = "tagId")
    private Tag tag;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private User createdBy;

    @OneToMany(mappedBy = "issue")
    private List<Comment> comments = new ArrayList<>();

    @Column
    private String title;

    @Column
    private String body;

    @Column
    private Boolean resolved;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;
}
