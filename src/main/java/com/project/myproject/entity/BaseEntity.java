package com.project.myproject.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JoinColumn(name = "updated_at")
    private LocalDateTime updatedAt;
}
