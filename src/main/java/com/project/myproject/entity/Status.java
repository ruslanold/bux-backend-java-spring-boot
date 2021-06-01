package com.project.myproject.entity;

import com.project.myproject.converter.StatusNameConverter;
import com.project.myproject.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    @Convert(converter = StatusNameConverter.class)
    private EStatus name;
}
