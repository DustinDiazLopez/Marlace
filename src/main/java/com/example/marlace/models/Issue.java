package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "issues")
public @Data
class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(length = 64)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private Boolean resolved = Boolean.FALSE;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
