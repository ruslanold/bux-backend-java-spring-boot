package com.project.myproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskDto {
    private long id;
    private String title;
    private String description;
    private double price;
    private TaskUserDto user;
    @JsonProperty(value = "category_id")
    private int categoryId;
    @JsonProperty(value = "reports_status")
    private List<TaskReportStatusDto> reportsStatus;
}
