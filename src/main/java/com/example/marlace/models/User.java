package com.example.marlace.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String description;
    private String profileImageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
