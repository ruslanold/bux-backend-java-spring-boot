package com.project.myproject.entity;

import com.project.myproject.converter.AuthorityNameConverter;
import com.project.myproject.enums.EAthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Convert(converter = AuthorityNameConverter.class)
    private EAthority name;
}
