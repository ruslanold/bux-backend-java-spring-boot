package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class AuthRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
