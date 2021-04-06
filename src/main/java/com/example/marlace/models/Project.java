package com.example.marlace.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer projectId;
    private String title;
    private String description;
    //    private Set<Issue> issues;
//    private Set<Tag> tags;
//    private Set<User> users;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
