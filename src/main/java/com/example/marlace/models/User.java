package com.example.marlace.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public @Data
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 128)
    private String firstName;

    @Column(length = 128)
    private String lastName;

    @ManyToMany(mappedBy = "users")
    private List<Project> projects;

    @Column(length = 384, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String password;

    @Column(length = 1024)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
