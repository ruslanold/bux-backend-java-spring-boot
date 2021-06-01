package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResponseDto {
    private String token;
    private UserAccountDto user;
}
