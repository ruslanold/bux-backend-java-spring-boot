package com.project.myproject.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @JoinColumn(name = "created_at")
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    @JoinColumn(name = "updated_at")
    private OffsetDateTime updatedAt;
}
