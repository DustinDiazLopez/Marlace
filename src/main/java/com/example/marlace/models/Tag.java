package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public @Data
class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tagId;

    @Column
    private String title;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;
}
