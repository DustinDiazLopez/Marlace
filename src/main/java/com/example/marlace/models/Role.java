package com.example.marlace.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public @Data
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @Column
    private String role;

    @Column
    private Timestamp createdAt;

    @Column
    private Timestamp updatedAt;
}
