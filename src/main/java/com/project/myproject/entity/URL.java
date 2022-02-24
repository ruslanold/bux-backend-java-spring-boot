package com.project.myproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @org.hibernate.validator.constraints.URL
    private String address;

    private boolean blocked;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "urls")
    List<Task> tasks = new ArrayList<>();

}
