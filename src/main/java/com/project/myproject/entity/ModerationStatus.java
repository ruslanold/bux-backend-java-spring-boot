package com.project.myproject.entity;

import com.project.myproject.converter.ModerationStatusNameConverter;
import com.project.myproject.enums.EModerationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ModerationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Convert(converter = ModerationStatusNameConverter.class)
    private EModerationStatus name;
}
