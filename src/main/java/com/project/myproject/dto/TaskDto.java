package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {
    private long id;
    private String title;
    private String description;
    private String descForApproval;
    private String priceWithCommission;
    private TaskUserInfoDto user;
    private int categoryId;
    private List<TaskReportStatusDto> reportsStatus;
}
