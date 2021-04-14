package com.example.marlace.models;

import com.example.marlace.utilities.Constants;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Comment> comments;

    @Column(length = 64)
    private String title;

    @Column(columnDefinition = Constants.ColumnDefinitions.TEXT)
    private String body;

    @Column
    private Boolean resolved = Boolean.FALSE;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
