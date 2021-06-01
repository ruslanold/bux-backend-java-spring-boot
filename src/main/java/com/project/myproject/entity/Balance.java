package com.project.myproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double total;
    //@OneToOne(mappedBy = "balance")
    //@JsonIgnore
    //@JsonIgnoreProperties
    //private User user;
}
