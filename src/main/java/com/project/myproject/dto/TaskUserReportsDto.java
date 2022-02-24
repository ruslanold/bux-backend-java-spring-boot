package com.project.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TaskUserReportsDto extends TaskDto {
    private List<ReportDto> reports;

    public TaskUserReportsDto(long id, String title, String description, String descForApproval, String price, TaskUserInfoDto user, int categoryId, List<TaskReportStatusDto> reportsStatus, List<ReportDto> reports) {
        super(id, title, description, descForApproval, price, user, categoryId, reportsStatus);
        this.reports = reports;
    }
}
