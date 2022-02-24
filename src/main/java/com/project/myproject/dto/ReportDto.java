package com.project.myproject.dto;

import com.project.myproject.enums.EModerationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDto {
    private long id;
    @NotNull
    private String body;
    private EModerationStatus status;
    private OffsetDateTime expiresAt;
    @NotNull
    private long taskId;
}
