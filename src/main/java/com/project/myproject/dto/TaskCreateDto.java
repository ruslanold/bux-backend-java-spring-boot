package com.project.myproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @Positive
    private double price;
    @Positive
    @JsonProperty(value = "category_id")
    private int categoryId;
    @Positive
    @JsonProperty(value = "user_id")
    private long userId;
}
