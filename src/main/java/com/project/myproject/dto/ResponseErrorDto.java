package com.project.myproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ResponseErrorDto {
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private int status;
}
