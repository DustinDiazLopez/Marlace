package com.example.marlace.models;

import com.example.marlace.utilities.Constants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
public @Data
class EmbeddedEntityMetadata {
    @Column(
            updatable = false,
            columnDefinition = Constants.ColumnDefinitions.ON_CREATE_TIMESTAMP
    )
    private Timestamp createdAt;

    @Column(
            updatable = false,
            columnDefinition = Constants.ColumnDefinitions.ON_UPDATE_TIMESTAMP
    )
    private Timestamp updatedAt;
}
