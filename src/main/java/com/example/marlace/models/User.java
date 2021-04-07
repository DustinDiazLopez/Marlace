package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table
public @Data
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToMany(mappedBy = "users")
    private List<Project> projects;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String description;

    @Column
    private String profileImageUrl;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;
}
