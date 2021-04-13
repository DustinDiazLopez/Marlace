package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
public @Data
class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @Column
    private String title;

    private EmbeddedEntityMetadata embeddedEntityMetadata;
}
