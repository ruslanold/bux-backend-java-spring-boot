package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskUserDto {
    private long id;
    private String name;
    private String image;
}
