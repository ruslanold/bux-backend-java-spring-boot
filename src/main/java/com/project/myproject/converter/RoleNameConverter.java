package com.project.myproject.converter;

import com.project.myproject.enums.ERole;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class RoleNameConverter extends EnumDbValueConverter<ERole, String> {
    public RoleNameConverter() {
        super(ERole.class);
    }
}
