package com.example.marlace.models;

import java.sql.Timestamp;
import java.util.List;

public class Issue {
    private Integer issueId;
    private Project project;
    private Tag tag;
    private User createdBy;
    private String title;
    private String body;
    private List<Comment> comments;
    private Boolean resolved;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
