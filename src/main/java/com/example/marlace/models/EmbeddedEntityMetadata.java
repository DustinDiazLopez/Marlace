package com.example.marlace.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
public @Data
class EmbeddedEntityMetadata {
    @Column(
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Timestamp createdAt;

    @Column(
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private Timestamp updatedAt;
}
