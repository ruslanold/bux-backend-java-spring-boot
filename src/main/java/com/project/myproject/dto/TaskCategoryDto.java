package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskCategoryDto {

    private int categoryId;
    List<TaskDto> tasks;

}
