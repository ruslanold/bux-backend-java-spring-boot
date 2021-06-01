package com.project.myproject.dto;

import com.project.myproject.validation.ValidPassword;
import com.project.myproject.validation.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRegister {
    @NotBlank
    @ValidUsername
    private String username;
    @NotBlank
    @ValidPassword
    private String password;
    @Email
    private String email;
}
