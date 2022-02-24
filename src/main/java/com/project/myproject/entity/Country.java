package com.project.myproject.entity;

import com.project.myproject.converter.CountryNameConverter;
import com.project.myproject.enums.ECountry;
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
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Convert(converter = CountryNameConverter.class)
    private ECountry name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "countries")
    private List<Task> tasks = new ArrayList<>();


}
