package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordUpdateRequestDto {
    private String password;
    private String token;
}
