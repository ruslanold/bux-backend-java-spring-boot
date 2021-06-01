package com.project.myproject.converter;

import com.project.myproject.enums.EAthority;

public class AuthorityNameConverter extends EnumDbValueConverter<EAthority, String>{
    public AuthorityNameConverter() {
        super(EAthority.class);
    }
}
