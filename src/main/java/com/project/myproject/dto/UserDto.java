package com.project.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Getter
public class UserDto {
    private String name;
    private double exp;
    private String role;
    private UserImageDto image;

    public UserDto(String name, double exp, String role, UserImageDto image) {
        this.name = name;
        this.exp = exp;
        this.role = role;
        this.image = image;
    }
}
