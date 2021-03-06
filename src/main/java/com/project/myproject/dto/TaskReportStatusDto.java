package com.project.myproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.myproject.enums.EModerationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskReportStatusDto {
    @JsonProperty(value = "status")
    private EModerationStatus statusName;
    @JsonProperty(value = "reports")
    private long numberOfReports;
}
