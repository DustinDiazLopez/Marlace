package com.example.marlace.models;

import java.sql.Timestamp;
import java.util.List;

public class Project {
    private Integer projectId;
    private String title;
    private String description;
    private List<Issue> issues;
    private List<Tag> tags;
    private List<User> users;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
