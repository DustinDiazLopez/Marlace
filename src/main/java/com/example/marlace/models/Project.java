package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table
public @Data
class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer projectId;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(mappedBy = "project")
    private List<Issue> issues;

    @ManyToMany
    private List<User> users;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;
}
