package com.project.myproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Task> tasks = new LinkedList<>();

}
