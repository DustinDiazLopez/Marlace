package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public @Data
class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(mappedBy = "project")
    private Set<Issue> issues;

    @ManyToMany
    private Set<User> users;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
