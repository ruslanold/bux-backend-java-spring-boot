package com.project.myproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String body;
    @Enumerated(EnumType.STRING)

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;
}
