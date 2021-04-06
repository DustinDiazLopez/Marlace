package com.example.marlace.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tagId;
    private String title;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
