package com.project.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class UserAccountDto extends UserDto {
    private BigDecimal balance;

    public UserAccountDto(String name, double exp, String role, UserImageDto image, BigDecimal balance) {
        super(name, exp, role, image);
        this.balance = balance;
    }
}
