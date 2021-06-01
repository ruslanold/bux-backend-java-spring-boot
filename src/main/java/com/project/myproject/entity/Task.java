package com.project.myproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Length(min = 4, max = 56)
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @Positive
    @NotNull
    private double price;

    //private boolean repeat;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany(mappedBy = "favoriteTasks", fetch = FetchType.LAZY)
    private List<User> favorites = new LinkedList<>();

    @ManyToMany(mappedBy = "hiddenTasks", fetch = FetchType.LAZY)
    private List<User> hidden = new LinkedList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<Report> reports = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
