package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRegister {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){4,15}[a-zA-Z0-9]$", message = "invalid username")
    private String username;
    @NotNull
    @Pattern(regexp = "^\\S{8,20}$", message = "invalid password")
    private String password;
    @NotNull
    @Email
    private String email;
    @Positive
    private Long referrerId;
}
