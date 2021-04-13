package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
public @Data
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column
    private String role;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
