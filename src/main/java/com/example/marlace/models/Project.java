package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    private List<Issue> issues;

    @ManyToMany
    private List<User> users;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
