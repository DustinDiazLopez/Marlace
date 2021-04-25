package com.example.marlace.models;

import com.example.marlace.utilities.Constants;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Project> projects = new HashSet<>();

    @Column(length = 384, unique = true)
    private String email;

    @Column(columnDefinition = Constants.ColumnDefinitions.TEXT)
    private String password;

    @Column(length = 1024)
    private String description;

    @Column(columnDefinition = Constants.ColumnDefinitions.TEXT)
    private String profileImageUrl;

    private EmbeddedEntityMetadata embeddedEntityMetadata = new EmbeddedEntityMetadata();

    /**
     * Censors sensitive data:
     * <ol>
     *     <li>Last name -> <b>null</b></li>
     *     <li>Hashed Password -> <b>null</b></li>
     *     <li>Email -> (characters between the first and the character before the '@' symbol are replaced with ***,
     *     e.g., example@example.com would be e***e@example.com)</li>
     * </ol>
     */
    public void censor() {
        this.setLastName(null);
        this.setPassword(null);
        this.setEmail(email.charAt(0) + "***" + email.substring(email.indexOf('@') - 1));
    }
}
