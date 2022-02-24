package com.project.myproject.dto;

import com.project.myproject.enums.EModerationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskUserDto {
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String descForApproval;
    @NotBlank
    private String price;
    private String balance;
    private int categoryId;
    private EModerationStatus status;
    private List<TaskReportStatusDto> reportsStatus;
}
